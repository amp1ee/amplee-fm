package com.amplee.myspringtest.config.application;

import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Util {
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
