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

    @RequestMapping("/cover")
    public String cover() {
        return "cover";
    }

    @RequestMapping("/include-head")
    public String includeHead() {
        return "include-head";
    }

}
