package com.rlsp.ecommerce.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ecmcontroller {

    @GetMapping
    public String index() {
        return "index";
    }
}