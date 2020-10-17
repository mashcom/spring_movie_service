package io.javabrains.movieinfoservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {

    @GetMapping
    public String index(){
        return "Hello Admin";
    }
}
