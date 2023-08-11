package com.example.EmailService.controller;

import com.example.EmailService.domain.EmailData;
import com.example.EmailService.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail-app-v1")
public class EmailController
{
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody EmailData emailData){

        return new ResponseEntity<>(emailSenderService.sendOtp(emailData), HttpStatus.OK);
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailData emailData){

        return new ResponseEntity<>(emailSenderService.sendEmail(emailData), HttpStatus.OK);
    }

}
