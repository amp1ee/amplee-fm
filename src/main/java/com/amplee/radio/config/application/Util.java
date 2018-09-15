package com.amplee.radio.config.application;

import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Util {
    static final String QUOTES_REGEXP = "^\"|\"$";

    public static Double getBrowserTimeZone(HttpServletRequest request){
        Cookie[] cookieArray = request.getCookies();
        if(cookieArray != null){
            for(Cookie cookie  : cookieArray){
                if("TZOffset".equals(cookie.getName())){
                    String offset = cookie.getValue();
                    return Double.parseDouble(offset);
                }
            }
        }

        return null;

    }

    public static String checkCookie(HttpServletRequest request, Double currentTimeZone, Model model) {
        if (currentTimeZone == null){

            String url = Util.getFullURL(request);
            System.out.println("Url="+url);
            model.addAttribute("redirectUrl", url);
            //Redirect to 'findTimeZone' for setting timezone.
            System.out.println("####Timezone is not set. Redirecting to findTimeZone.jsp for setting timezone.");
            return "findTimeZonePage";

        }

        else return "player";
    }

    public static String readUrl(String urlString) {
        URL url;
        InputStream inStream;
        StringBuilder buffer = null;

        try {
            url = new URL(urlString);
            inStream = url.openStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream))) {
            buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (buffer != null) ? buffer.toString() : null;
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
