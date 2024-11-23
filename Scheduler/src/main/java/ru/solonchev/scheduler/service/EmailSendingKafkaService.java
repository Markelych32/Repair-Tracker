package ru.solonchev.scheduler.service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.solonchev.event.EmailSendingTasksEvent;
import ru.solonchev.scheduler.config.dto.AnalyseResponseDto;
import ru.solonchev.scheduler.utils.EmailSendingTasksEventGenerator;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSendingKafkaService implements EmailSendingService {

    private final KafkaTemplate<String, EmailSendingTasksEvent> kafkaTemplate;

    @Override
    public void sendEmailMessage(AnalyseResponseDto dto) {
        String emailId = UUID.randomUUID().toString();
        var event = EmailSendingTasksEventGenerator.generate(dto);

        ProducerRecord<String, EmailSendingTasksEvent> record = new ProducerRecord<>(
                "email-sending-tasks-topic",
                emailId,
                event
        );
        record.headers().add("emailId", UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

        CompletableFuture<SendResult<String, EmailSendingTasksEvent>> future =
                kafkaTemplate.send(record);

        future.whenComplete((result, exception) -> {
            if (Objects.nonNull(exception)) {
                log.error("Failed to send message", exception);
            } else {
                log.info("Message was send successfully: {}", result.getRecordMetadata());
            }
        });
    }
}
