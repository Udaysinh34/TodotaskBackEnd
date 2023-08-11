package com.niit.TodoList.proxy;

import com.niit.TodoList.domain.EmailData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "email-service-app",url = "localhost:9009")
public interface MailProxy {
    @PostMapping("/mail-app-v1/send-mail")
    ResponseEntity<?> sendEmail(@RequestBody EmailData emailData);
    @PostMapping("/mail-app-v1/send-otp")
    ResponseEntity<?> sendOtp(@RequestBody EmailData emailData);
}
