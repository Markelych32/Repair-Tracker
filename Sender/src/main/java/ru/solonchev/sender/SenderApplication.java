package ru.solonchev.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.solonchev.sender.service.NotificationEmailSender;

@SpringBootApplication
@RequiredArgsConstructor
public class SenderApplication {

    private final NotificationEmailSender emailSender;

    public static void main(String[] args) {
        SpringApplication.run(SenderApplication.class, args);
    }
}
