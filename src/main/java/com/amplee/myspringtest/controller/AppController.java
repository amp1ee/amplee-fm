package com.amplee.myspringtest.controller;

import com.amplee.myspringtest.config.application.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class AppController {

    @RequestMapping("/")
    public String hello(HttpServletRequest request, Model model){

        //Double currentTimeZone = Util.getBrowserTimeZone(request);
        //System.out.println("currentTimeZone: "+currentTimeZone);
        //return Util.checkCookie(request, currentTimeZone, model);
        return "player";
    }

    @RequestMapping("/player")
    public String player() {
        return "player";
    }

    @RequestMapping("/titles")
    public void titles(Model model) {

    }

}
