package com.amplee.radio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AppController {

    @RequestMapping("/")
    public String hello(HttpServletRequest request, Model model){

        return "player";
    }

    @RequestMapping("/player")
    public String player() {
        return "player";
    }

    @RequestMapping("/titles")
    public void titles(Model model) {
    }

//    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
//    public ModelAndView adminPage() {
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("title", "Spring Security Login Form - Database Authentication");
//        model.addObject("message", "This page is for ROLE_ADMIN only!");
//        model.setViewName("admin");
//        return model;
//    }

//    @RequestMapping(value = "/loginpage", method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
//                              @RequestParam(value = "logout", required = false) String logout) {
//
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Invalid username and password!");
//        }
//
//        if (logout != null) {
//            model.addObject("msg", "You've been logged out successfully.");
//        }
//        model.setViewName("loginpage");
//
//        return model;
//    }
//    private static ResultSet rs;
//    private static Statement stmt;
//    private static Connection connection;
//    private static String query = "SELECT name, password FROM users";
//
//    @RequestMapping(value = "/loginpage", method = RequestMethod.POST)
//    public ModelAndView check(HttpServletRequest request) {
//
//        ModelAndView model = new ModelAndView();
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useSSL=false&serverTimezone=UTC", "***REMOVED***", "***REMOVED***");
//            stmt = connection.createStatement();
//            rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String pw = rs.getString("password");
//                if (name.equals(request.getParameter("username")) && pw.equals(request.getParameter("password"))) {
//                    model.setViewName("player");
//                    return model;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                stmt.close();
//                rs.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("We are here");
//        model.setViewName("loginpage");
//        return model;
//    }
//
//    //for 403 access denied page
//    @RequestMapping(value = "/403", method = RequestMethod.GET)
//    public ModelAndView accesssDenied() {
//
//        ModelAndView model = new ModelAndView();
//
//        //check if user is login
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            UserDetails userDetail = (UserDetails) auth.getPrincipal();
//            model.addObject("username", userDetail.getUsername());
//        }
//
//        model.setViewName("403");
//        return model;
//
//    }

    @RequestMapping("/cover")
    public String cover() {
        return "cover";
    }

    @RequestMapping("/include-head")
    public String includeHead() {
        return "include-head";
    }

}
