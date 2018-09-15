package com.amplee.radio.config.application;

import com.google.gson.*;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class RecentTracks implements Serializable {
    private static volatile RecentTracks instance;
    private static JsonParser jsonParser = new JsonParser();
    private static final String imgDir = "./images/image/";
    private static final String tagOpenW = "<img align=\"middle\" width=\"110\" src=\"";
    private static final String tagOpen = "<img align=\"middle\" src=\"";
    private static final String tagClose = "\">";
    private static Map<String, String> imgUrlsMap = new ImgHashMap<>(10);
    private static final String vDash = "\\|";
    private static final String colon = ":";
    private static final char c = 9899;
    private static final String circle = " " + c + " ";
    private static final String defImage = imgDir + "songinfo.jpeg";
    private static final String defImgTag = tagOpen + defImage + tagClose;
    private static final String defCover = imgDir + "music-elems.png";
    private static final String defCoverTag = tagOpenW + defCover + tagClose;
    private static String curImage = defCover;
    private static String curImgTag = tagOpenW + curImage + tagClose;
    private static final String baseUrl_lastFM = "http://ws.audioscrobbler.com/2.0/";
    private static final String _text = "#text";
    private final String apiKey_lastFM = System.getenv("LASTFM_KEY");
    private final String defArtist = "Unknown Artist";
    private final String defTrack = "Unknown Track";

    private RecentTracks() {}

    public static RecentTracks getInstance() {
        RecentTracks localInstance = instance;
        if (localInstance == null) {
            synchronized (RecentTracks.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RecentTracks();
                    System.out.println("Tracklist class instance initialized");
                }
            }
        }
        return localInstance;
    }

    public static String getDefCoverImage() {
        return defImgTag;
    }

    public String getCurCoverImage() {

        return curImgTag;
    }

    public void setCurImage(String curTrack) {
        String get = getImageTag(curTrack, true);
        if (get.equals("<img width=\"110\" align=\"middle\" src=\"\">")) {
            curImgTag = defCoverTag;
        } else
            curImgTag = get;
    }

    public ArrayList<String> getTitles() {
        int amount = 10;
        ArrayList<String> titles = new ArrayList<>();
        String artist;
        String track;
        String title;
        String user = "AmpIy";
        JsonElement element;
        JsonObject jObj;
        JsonArray jArr;

        // Parsing from LastFM
        String request = baseUrl_lastFM.concat("?method=user.getrecenttracks")
                .concat("&user=").concat(user)
                .concat("&api_key=").concat(apiKey_lastFM)
                .concat("&limit=").concat(String.valueOf(amount))
                .concat("&format=json");
        String json = Util.readUrl(request);
        if (json == null)
            return null;
        element = jsonParser.parse(json);
        try {
            jObj = element.getAsJsonObject().getAsJsonObject("recenttracks");
            jArr = jObj.getAsJsonArray("track");
            for (JsonElement e : jArr) {
                jObj = e.getAsJsonObject();
                artist = jObj.getAsJsonObject("artist").get(_text).toString();
                artist = artist.replaceAll(Util.QUOTES_REGEXP, "");
                track = jObj.get("name").toString();
                track = track.replaceAll(Util.QUOTES_REGEXP, "");
                if (artist == null)
                    artist = defArtist;
                if (track == null)
                    track = defTrack;
                title = artist.concat(" - ").concat(track);
                titles.add(circle + title);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return titles;
    }

    public String getImageTag(String title) {
        return getImageTag(title, false);
    }

    public String getImageTag(String title, boolean cover) {

        title = title.substring(title.indexOf(c) + 1, title.length());  // Cutting out the time info;
        String[] split = title.split("-");
        String cArtist = split[0].trim();
        String cTrack;

        try {
            cTrack = split[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            cTrack = "Untitled";
        }

        String cArtistEnc = null;
        String cTrackEnc = null;
        try {
            cArtistEnc = UriUtils.encode(cArtist, "UTF-8");
            cTrackEnc = UriUtils.encode(cTrack, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urlTrack, urlArtist;

        urlArtist = baseUrl_lastFM.concat("?method=artist.getInfo&" +
                "artist=" + cArtistEnc +
                "&api_key=" + apiKey_lastFM + "&format=json");
        urlTrack = urlArtist.replace("=artist", "=track").concat("&track=" + cTrackEnc);

        String imgTag = null;

        if (!cover) {

            if (!imgUrlsMap.containsKey(title)) {

                imgTag = handleTag(urlArtist, urlTrack);

                if (imgTag == null || imgTag.trim().equals("<img align=\"middle\" src=\"\">"))
                    imgUrlsMap.put(title, defImgTag);
                else
                    imgUrlsMap.put(title, imgTag);

            }

            return imgUrlsMap.get(title);

        } else {    //(if cover image)

            try {
                imgTag = handleTag(urlArtist, urlTrack, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return imgTag != null ? imgTag : defCoverTag;
        }

    }

    private String handleTag(String urlArtist, String urlTrack) {
        return handleTag(urlArtist, urlTrack, false);
    }

    private String handleTag(String urlArtist, String urlTrack, boolean cover) {
        String imgTag = parseCovers(urlTrack, false, cover);

        if (imgTag != null) {
            if (imgTag.trim().equals("<img align=\"middle\" src=\"\">"))
                imgTag = parseCovers(urlArtist, true, cover);
        } else {
            imgTag = parseCovers(urlArtist, true, cover);
        }

        if (imgTag != null && !imgTag.trim().equals("<img align=\"middle\" src=\"\">"))
            imgTag = imgTag.replace("https", "http");

        return imgTag;
    }

    private String parseCovers(String urlPath, boolean isMethodArtist, boolean cover) {
        String json = Util.readUrl(urlPath);
        if (json == null)
            return null;
        JsonElement element = jsonParser.parse(json);
        JsonObject jObj = element.getAsJsonObject();
        JsonArray imgArr = null;

        if (!isMethodArtist) {

            try {
                jObj = jObj.getAsJsonObject("track");
                jObj = jObj.getAsJsonObject("album");
                imgArr = jObj.getAsJsonArray("image");
            } catch (NullPointerException e) {
                e.getMessage();
            }
            if (!cover)
                return checkImgArr(imgArr, false);
            else
                return checkImgArr(imgArr, true);

        } else {

            imgArr = null;
            try {
                jObj = jObj.getAsJsonObject("artist");
                imgArr = jObj.getAsJsonArray("image");
            } catch (NullPointerException e) {
                e.getMessage();
            }

            if (!cover)
                return checkImgArr(imgArr, false);
            else
                return checkImgArr(imgArr, true);
        }
    }

    private String checkImgArr(JsonArray imgArr, boolean cover) {
        if (imgArr != null) {
            JsonObject jObj = imgArr.get(0).getAsJsonObject();
            String imgUrl = jObj.get(_text).toString();

            JsonObject jCoverObj = imgArr.get(2).getAsJsonObject();
            String coverUrl = jCoverObj.get(_text).toString();
            if (!cover)
                return "<img align=\"middle\" src=" + imgUrl + ">";
            else
                return "<img width=\"110\" align=\"middle\" src=" + coverUrl + ">";

        } else return null;
    }

    private String subString(String s, int at, int last) {
        boolean isZero = s.charAt(at)=='0';
        return s.substring(isZero ? at + 1 : at, last);
    }
    private String subString(String s, int at) {
        return subString(s, at, s.length());
    }
}
