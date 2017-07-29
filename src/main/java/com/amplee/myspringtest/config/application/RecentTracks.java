package com.amplee.myspringtest.config.application;

import com.google.gson.*;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

public class RecentTracks implements Serializable {

    private static volatile RecentTracks instance;
    private static Map<String, String> imgUrlsMap = new ImgHashMap<>(10);
    private static final String vdash = "\\|";
    private static final String colon = ":";
    private static final char c = 9899;
    private static final String circle = " " + c + " ";
    private static final String defImage = "./skin/image/songinfo.jpeg";

    private final TimeZone tz = new GregorianCalendar().getTimeZone();       // Server's time zone
    private final LocalDateTime dt = LocalDateTime.now();                   // time now
    private final ZoneId zone = ZoneId.of(tz.getID());                      // zone id
    private final ZonedDateTime zdt = dt.atZone(zone);                      // zonedDateTime
    private final ZoneOffset zOffs = zdt.getOffset();                       // Server's zone offset
    private final String zOffset = zOffs.toString();                        //      -//-
    private final String zHOffset =
            subString(zOffset, 1, zOffset.indexOf(':'));                //Server offset in hours
    private final String zMOffset =
            subString(zOffset, zOffset.indexOf(':') + 1);               //Server offset in minutes

    private final int sysHOffs = Integer.parseInt(zHOffset);                // Server offset in hours
    private final int sysMOffs = Integer.parseInt(zMOffset);                // Server offset in minutes

    private RecentTracks() {}

    public static RecentTracks getInstance() {
        RecentTracks localInstance = instance;
        if (localInstance == null) {
            synchronized (RecentTracks.class) {
                localInstance = instance;
                if (localInstance == null) {
                    System.out.println("new RecentTracks() created");
                    instance = localInstance = new RecentTracks();
                }
            }
        }
        return localInstance;
    }

    public ArrayList<String> getTitles(Double offset) {

        ArrayList<String> titles = new ArrayList<>();
        String FILE_NAME = "D:/Programs/Icecast/log/playlist.log";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] vDashSplit = line.split(vdash);
                String[] colonSplit = line.split(colon);
                String title;
                try {
                    title = vDashSplit[3];
                } catch (ArrayIndexOutOfBoundsException e) {
                    title = "Unknown Artist - Unknown Name";
                }

                /* defining hour & min*/
                String hour = colonSplit[1];
                hour = subString(hour, 0);
                int iHour = Integer.parseInt(hour);

                String min = colonSplit[2];
                min = subString(min, 0);
                int iMin = Integer.parseInt(min);

                int offsetIntValue = offset.intValue();
                iHour = iHour + (offsetIntValue - sysHOffs);             // h + clientOffset(h) - serverOffset(h)
                boolean signum = offsetIntValue >= 0;

                String cliOffs = offset.toString();                         // clientOffset
                int clientMins = Integer.parseInt(cliOffs.
                        substring(cliOffs.indexOf('.') + 1));               // clientOffset in percents of an hour
                if (clientMins == 5) {clientMins = 30;}                     // percents/hour to minutes
                else if (clientMins == 75) {clientMins = 45;}               //  -//-
                else if (clientMins == 25) {clientMins = 15;}

                iMin = iMin + (signum ? clientMins : -clientMins - sysMOffs);                      // m (+/-)clientOffset(min) - serverOffset(min)

                if (iHour >= 24) {
                    iHour -= 24;
                }
                if (iHour < 0) {
                    iHour += 24;
                }

                if (iMin >= 60) {
                    iMin -= 60;
                    iHour += 1;
                }
                if (iMin < 0) {
                    iMin += 60;
                    iHour -= 1;
                }

                hour = String.valueOf(iHour);
                hour = hour.length() == 2 ? hour : "0".concat(hour);

                min = String.valueOf(iMin);
                min = min.length() == 2 ? min : "0".concat(min);

                String time = hour + colon + min;

                titles.add(time + circle + title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return titles;
    }

    private String subString(String s, int at, int last) {
        boolean isZero = s.charAt(at)=='0';
        return s.substring(isZero ? at + 1 : at, last);
    }
    private String subString(String s, int at) {
        return subString(s, at, s.length());
    }

    public String getImageTag(String title) {
        title = title.substring(title.indexOf(c) + 1, title.length());  //Cutting out the time info;
        //System.out.println(title);
        String[]
        split = title.split("-");
        String cartist = split[0].trim();
        String ctrack;
        try {
            ctrack = split[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            ctrack = "Untitled";
        }

        String cArtistEnc = null;
        String cTrackEnc = null;
        try {
            cArtistEnc = UriUtils.encode(cartist, "UTF-8");
            cTrackEnc = UriUtils.encode(ctrack,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String apiKey = "bb5f88ff636419e6661edcccfd116638";
        String urlTrack, urlArtist;
        String baseUrl = "http://ws.audioscrobbler.com/2.0/";

        urlArtist = baseUrl.concat("?method=artist.getInfo&" +
                "artist=" + cArtistEnc +
                "&api_key=" + apiKey +"&format=json");
        urlTrack = urlArtist.replace("=artist","=track").concat("&track=" + cTrackEnc);

        String imgTag = null;

        if (!imgUrlsMap.containsKey(title)) {
            try {
                imgTag = parseJson(urlTrack, false);
            } catch (Exception e) {
                e.getMessage();
            }
            if (imgTag != null) {
                if (!imgTag.trim().equals("<img src=\"\">")) {
                    imgTag = imgTag.replace("https", "http");
                } else {
                    try {
                        imgTag = parseJson(urlArtist, true);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            } else {
                try {
                    imgTag = parseJson(urlArtist, true);
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            String defImgTag = "<img src=\"" + defImage + "\">";

            if (imgTag == null)
                imgUrlsMap.put(title, defImgTag);
            else if (imgTag.trim().equals("<img src=\"\">"))
                imgUrlsMap.put(title, defImgTag);
            else
                imgUrlsMap.put(title, imgTag);

        }

        return imgUrlsMap.get(title);
    }

    private String parseJson(String urlPath, boolean isMethodArtist) throws Exception {
        JsonParser parser = new JsonParser();
        String json = readUrl(urlPath);
        JsonElement element = parser.parse(json);
        JsonObject jobj = element.getAsJsonObject();
        JsonArray imgArr = null;

        if (!isMethodArtist) {
            //System.out.println("track.getInfo() " + urlPath);
            try {
                jobj = jobj.getAsJsonObject("track");
                jobj = jobj.getAsJsonObject("album");
                imgArr = jobj.getAsJsonArray("image");
            } catch (NullPointerException e) {
                e.getMessage();
            }
            return checkImgArr(imgArr);

        } else {
            //System.out.println("artist.getInfo() " + urlPath);
            imgArr = null;

            try {
                jobj = jobj.getAsJsonObject("artist");
                imgArr = jobj.getAsJsonArray("image");
            } catch (NullPointerException e) {
                e.getMessage();
            }
            return checkImgArr(imgArr);
        }
    }

    private String checkImgArr(JsonArray imgArr) {
        if (imgArr != null) {
            JsonObject jobj = imgArr.get(0).getAsJsonObject();
            String imgUrl = jobj.get("#text").toString();
            return "<img src=" + imgUrl + ">";
        } else return null;
    }

    private static String readUrl(String urlString) throws Exception {

        URL url = new URL(urlString);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        }
    }
}
