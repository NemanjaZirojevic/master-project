package com.reactive.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {


    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ModelAndView displayMainPage()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;

    }
}
