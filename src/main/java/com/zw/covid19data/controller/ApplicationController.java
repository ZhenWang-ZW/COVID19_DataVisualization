package com.zw.covid19data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    @GetMapping("/index.html")
    public String index(Model model) {
        String name = "Zhen";
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/addupdate.html")
    public String add_update(Model model) {
        String name = "Wang";
        model.addAttribute("name", name);
        return "add_update";
    }

}
