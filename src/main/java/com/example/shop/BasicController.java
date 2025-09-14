package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@Controller
public class BasicController {
    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/about")
    public String hello2() {
        return "index.html";
    }

    @GetMapping("/date")
    @ResponseBody
    String date() {
        return ZonedDateTime.now().toString();
    }
}