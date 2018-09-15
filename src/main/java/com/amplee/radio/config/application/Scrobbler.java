package com.amplee.radio.config.application;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Result.Status;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleData;
import de.umass.lastfm.scrobble.ScrobbleResult;

public class Scrobbler {

    private static volatile Scrobbler instance;
    private static String curTrack = null;
    private String apiKey, apiSecret;
    private JsonParser parser;
    private boolean connected = false;
    private Session session;

    private Scrobbler(String username, String password) {
        apiKey = System.getenv("LASTFM_KEY");
        apiSecret = System.getenv("LASTFM_SECRET");
        session = Authenticator.getMobileSession(username, password, apiKey, apiSecret);
        try {
            session.getKey();
            connected = true;
        } catch (NullPointerException e) {
            connected = false;
        }
        parser = new JsonParser();
    }

    public static Scrobbler getInstance(String username, String password) {
        Scrobbler localInstance = instance;
        if (localInstance == null) {
            synchronized (Scrobbler.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Scrobbler(username, password);
                    System.out.println("Scrobbler instance initialized");
                }
            }
        }
        return localInstance;
    }

    public boolean scrobble() {
        ScrobbleResult res;
        ScrobbleData sd;
        Status status;
        int timeNow;
        String trackTitle, artist = null, name = null;
        boolean result = true;
        String trackUrl = "http://***REMOVED***:9000/status-json.xsl";
        JsonObject jObj;
        String json = Util.readUrl(trackUrl);
        if (json == null)
            return false;
        jObj = parser.parse(json).getAsJsonObject().getAsJsonObject("icestats");
        jObj = jObj.getAsJsonObject("source");
        trackTitle = jObj.getAsJsonPrimitive("title").toString();
        trackTitle = trackTitle.replaceAll(Util.QUOTES_REGEXP, "");
        if (trackTitle == null)
            return false;
        else if (trackTitle.equals(curTrack))
            return false;
        else
            curTrack = trackTitle;

        String[] titleArr = trackTitle.split("-");
        try {
            artist = titleArr[0];
            name = titleArr[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            if (artist == null)
                artist = "Unknown";
            if (name == null)
                name = "Unknown";
        }
        timeNow = (int) (System.currentTimeMillis() / 1000);
        sd = new ScrobbleData(artist.trim(), name.trim(), timeNow);
        res = Track.scrobble(sd, session);
        status = res.getStatus();
        if ((status.equals(Status.FAILED)) || (res.getIgnoredMessage() != null)) {
            result = false;
        }
        return result;
    }

    public boolean isConnected() {
        return connected;
    }
}
