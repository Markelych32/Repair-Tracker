package ru.solonchev.sender.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.solonchev.event.EmailSendingTasksEvent;
import ru.solonchev.sender.service.NotificationEmailSender;

@Component
@KafkaListener(topics = "email-sending-tasks-topic")
@Slf4j
@RequiredArgsConstructor
public class EmailSendingTasksEventHandler {

    private final NotificationEmailSender notificationEmailSender;

    @KafkaHandler
    public void handle(@Payload EmailSendingTasksEvent event) {
        log.info("Get Event: {}", event);
        if (!(event.finished().isEmpty() && event.active().isEmpty())) {
            notificationEmailSender.send(event);
        }
    }
}
