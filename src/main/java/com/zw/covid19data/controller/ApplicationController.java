package com.zw.covid19data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    @GetMapping
    public String index(Model model){
        String name = "Zhen";
        model.addAttribute("name", name);
        return "index";
    }
}
