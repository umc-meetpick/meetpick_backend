package com.umc.meetpick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TempController {

    @GetMapping("/temp")
    public String Temp() {
        return "temp";
    }
}
