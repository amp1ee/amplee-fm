package com.amplee.radio.controller;

import com.amplee.radio.config.application.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@RequestParam String userId, @RequestParam String password,
                              HttpSession session, Model model) {
        String customer = customerService.loginCustomer(userId, password);
        if (customer == null) {
            model.addAttribute("loginError", "Error logging in. Please try again");
            return "login";
        }
        session.setAttribute("loggedInUser", customer);
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
        return "register";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String userId, @RequestParam String password, @RequestParam String email, HttpSession session, Model model) {

        int registered = customerService.registerCustomer(userId, password, email);
        if (registered == 1) {
            model.addAttribute("registerSuccess", "Registration was successful. <a href=\"login\">Please login</a>");

        } else if (registered == 0) {
            model.addAttribute("registerError", "Registration error: user with such name already exists");
        } else model.addAttribute("registerError", "Such email already registered. <a href=\"login\">Try to login</a>");
        return "register";
    }
}
