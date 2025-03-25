package com.charly.timesnp_back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserVerificationController para manejar la verificacion de Email e INE
 */

@RestController
@RequestMapping("/api/verification")
public class UserVerificationController {

    @GetMapping("/email")
    public String verifyEmail() {
        return "Email verificado";
    }

    @GetMapping("/ine")
    public String verifyINE() {
        return "INE verificado";
    }


}
