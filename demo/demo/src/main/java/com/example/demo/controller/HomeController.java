package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String greet(){
        return "hey! this msg from harsh";
    }
    @RequestMapping("/about")
    public String abo(){
        return "hey! this msg from about section";
    }
}
