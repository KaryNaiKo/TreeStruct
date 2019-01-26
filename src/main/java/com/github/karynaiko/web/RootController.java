package com.github.karynaiko.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:tree";
    }

    @GetMapping("/tree")
    public String meals(Model model) {
        return "tree";
    }
}
