package com.amplee.myspringtest.config.application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RecentTracks {

        private static volatile RecentTracks instance;
        private static String vdash = "\\|";
        private static String colon = ":";

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


    public List<String> getTitles() {

        List<String> titles = new ArrayList<>();
        String FILE_NAME = "D:/Programs/Icecast/log/playlist.log";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] infos = line.split(vdash);
                String[] infos2 = line.split(colon);
                String title = infos[3];
                String time = infos2[1]+ colon + infos2[2];
                titles.add(time + " " + title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return titles;
    }
}
