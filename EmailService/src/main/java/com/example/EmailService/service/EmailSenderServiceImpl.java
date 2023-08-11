package com.example.EmailService.service;

import com.example.EmailService.domain.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendOtp(EmailData emailData) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailData.getReceiver());
            mailMessage.setText(emailData.getMessage());
            mailMessage.setSubject(emailData.getSubject());
            javaMailSender.send(mailMessage);
            return "mail Sent to " + emailData.getReceiver();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Sending mail failed . . .";
        }

    }


    @Override
    public String sendEmail(EmailData emailData) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailData.getReceiver());
            mailMessage.setText(emailData.getMessage());
            mailMessage.setSubject(emailData.getSubject());
            javaMailSender.send(mailMessage);
            return "mail Sent to " + emailData.getReceiver();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Sending mail failed . . .";
        }

    }
}
