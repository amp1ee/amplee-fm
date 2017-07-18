package com.amplee.myspringtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AppController {

    @RequestMapping("/")
    public String hello(Model model){

        model.addAttribute("hello", "Hello my first Spring App!");
        return "hello";

    }

    @RequestMapping("/service")
    public String service(Model model) {

        model.addAttribute("service", "This is just bullshit");
        return "service";
    }

    @RequestMapping("/player")
    public void player(Model model) {

    }

    @RequestMapping("/titles")
    public void titles(Model model) {

    }
}
