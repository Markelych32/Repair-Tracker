package ru.solonchev.sender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.solonchev.event.EmailSendingTasksEvent;

@Service
@RequiredArgsConstructor
public class NotificationEmailSender {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public void send(EmailSendingTasksEvent event) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(event.email());
        mailMessage.setSubject(event.headerOfLetter());
        mailMessage.setText(createEmailMessage(event));
        mailMessage.setFrom(from);

        mailSender.send(mailMessage);
    }

    private String createEmailMessage(EmailSendingTasksEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        if (event.finished() != null && !event.finished().isEmpty()) {
            stringBuilder.append("Finished tasks:\n");
            for (String task : event.finished()) {
                stringBuilder.append(task).append("\n");
            }
        } else {
            stringBuilder.append("No finished tasks.").append("\n");
        }
        if (event.active() != null && !event.active().isEmpty()) {
            stringBuilder.append("\nActive tasks: \n");
            for (String task : event.active()) {
                stringBuilder.append(task).append("\n");
            }
        }
        return stringBuilder.toString();
    }


}
