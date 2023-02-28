package com.example.hanghaeworld.service;

import com.example.hanghaeworld.dto.MailDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    private String sender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendMail(MailDto mailDto) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailDto.getEmail());
        message.setFrom(sender);
        message.setSubject(String.format("%S님, 회원가입이 완료되었습니다.", mailDto.getName()));
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
