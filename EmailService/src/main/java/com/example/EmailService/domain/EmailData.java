package com.example.EmailService.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailData
{
    public String receiver,message,subject,attachment;
    public int otp;
}
