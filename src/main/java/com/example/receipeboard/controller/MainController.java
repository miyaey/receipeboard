package com.example.receipeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {

        return "redirect:/post/list";
    }
    @GetMapping("/post/listpage")
    public String listpageing() {

        return "redirect:/post/list";
    }

}