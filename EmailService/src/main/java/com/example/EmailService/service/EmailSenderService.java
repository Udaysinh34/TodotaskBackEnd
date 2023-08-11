package com.example.EmailService.service;

import com.example.EmailService.domain.EmailData;

public interface EmailSenderService
{
    public abstract String sendOtp(EmailData emailData);
    public abstract String sendEmail(EmailData emailData);
}
