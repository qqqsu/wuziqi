package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class IndexController {

    @GetMapping(value = "/toIndex")
    public String toIndexPage() {
        return "index";
    }

    @GetMapping(value = "/")
    public String toIndexPageDirectly() {
        return "index";
    }


}
