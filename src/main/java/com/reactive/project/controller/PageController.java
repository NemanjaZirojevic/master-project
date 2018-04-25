package com.reactive.project.controller;

import com.reactive.project.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {


    @RequestMapping(value = "/register")
    public ModelAndView registration()
    {
        ModelAndView modelAndView = new ModelAndView("registration");
        User user = new User();
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(Model model) {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @RequestMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping(value = "/stockDetails/{id}",method = RequestMethod.GET)
    public ModelAndView displayStockDetails(@PathVariable("id") int id)
    {
        ModelAndView modelAndView = new ModelAndView("stock");
        return modelAndView;
    }
}
