package com.reactive.project.controller;

import com.reactive.project.domain.User;
import com.reactive.project.repository.StockRepository;
import com.reactive.project.service.UserService;
import com.reactive.project.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register")
    public ModelAndView registration()
    {
        ModelAndView modelAndView = new ModelAndView("registration");
        User user = new User();
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "statisticsAdministration",method = RequestMethod.GET)
    public ModelAndView metrics()
    {
        ModelAndView modelAndView = new ModelAndView("admin/statistics");
        return modelAndView;
    }

    @RequestMapping(value = "userAdministration",method = RequestMethod.GET)
    public ModelAndView userAdministration(){
        ModelAndView modelAndView = new ModelAndView("admin/user_administration");
        return  modelAndView;
    }
    @RequestMapping(value = "companiesAdministration",method = RequestMethod.GET)
    public ModelAndView companiesAdministration(){
        ModelAndView modelAndView = new ModelAndView("admin/companies_administration");
        return  modelAndView;
    }


    @RequestMapping("/")
    public ModelAndView root(@AuthenticationPrincipal Principal principal) {
        ModelAndView modelAndView = new ModelAndView("main");
        UserDetails myUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User current = userService.findByEmail(myUserDetails.getName());
        String amount = null;
        if(current!=null)
        {
            try {
                amount = current.getAccount().getSum();
            }catch (Exception ex)
            {
                ex.printStackTrace();

            }

        }
        modelAndView.addObject("avaiableSum",amount);
        modelAndView.addObject("sum",amount);
        return modelAndView;
    }
    @RequestMapping("/users")
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView("users");
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
