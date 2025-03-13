package com.charly.timesnp_back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testing")
public class TestingController {


    @GetMapping("/private/admin")
    public String getTesting() {
        return "Here is the testing with authentication and admin";
    }

    @GetMapping("/private")
    public String getTestingNoAdmin() {
        return "Here is the testing with authentication but not admin";
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
