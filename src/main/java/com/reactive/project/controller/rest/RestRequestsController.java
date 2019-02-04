package com.reactive.project.controller.rest;


import com.reactive.project.domain.Account;
import com.reactive.project.domain.User;
import com.reactive.project.domain.UserRegistrationDto;
import com.reactive.project.domain.UserWrapper;
import com.reactive.project.repository.StockRepository;
import com.reactive.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@EnableMongoRepositories
@RestController
@RequestMapping("/rest")
public class RestRequestsController {

    @Autowired
    MetricsEndpoint metricsEndpoint;

    @Autowired
    UserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "statistics",method = RequestMethod.GET)
    public Map<String, Object> metrics()
    {
        return metricsEndpoint.invoke();
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/findAllUsers",method = RequestMethod.GET)
    public UserWrapper findAllUsers()
    {
       List <User> users = userService.findAll();
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setData(users);
       return userWrapper;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/deleteUser",method = RequestMethod.DELETE)
    public void deleteUser(@RequestBody User user)
    {
        userService.delete(user);
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/updateUsers",method = RequestMethod.POST)
    public void updateUser(@RequestBody User user)
    {

        userService.save(user);
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public void addNewUser(@RequestBody UserRegistrationDto userDto)
    {
        System.out.println(userDto);

        userService.save(userDto);
    }

    @RequestMapping(value = "/get/user/{id}",method = RequestMethod.GET)
    public Account getUserById(@PathVariable("id")Long id)
    {
        Optional<User> user = userService.findById(id);
        User current = null;
        if(user!=null)
        {
            current = user.get();
        }
        return current.getAccount();
    }

}
