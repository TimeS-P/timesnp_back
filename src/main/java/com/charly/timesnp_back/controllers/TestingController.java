package com.charly.timesnp_back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testing")
public class TestingController {


    @GetMapping("/private")
    public String getTesting() {
        return "Here is the testing with authentication";
    }

    @GetMapping("/public")
    public String getTestingPublic() {
        return "Here is the testing without authentication";
    }

    @PostMapping("/private")
    public String postTesting() {
        return "Here is the POST testing with authentication";
    }

}
