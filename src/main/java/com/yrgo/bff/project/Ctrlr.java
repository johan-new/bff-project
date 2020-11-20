package com.yrgo.bff.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ctrlr {
    @RequestMapping("/")
    String hello(){
        return "Hello";
    }

    @GetMapping("/users")
    String Mahatma_Ghandi() {
        return "Mahatma Ghandi";
    }

}
