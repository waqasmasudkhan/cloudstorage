package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    UserService userService;

    public SignupController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public String getSignup(@ModelAttribute("user") Users user){
        return "signup";
    }

    @PostMapping
    public String postSignup(@ModelAttribute("user") Users user, Model model){

        if(userService.isUsernameAvailable(user)){

            int key= userService.addUser(user);
            if(key<0)
                model.addAttribute("signupError",true);
            else
                model.addAttribute("signupSuccess",true);
                return "redirect:login";
        }
        else{
            model.addAttribute("signupError",true);
        }
        return "signup";
    }


}
