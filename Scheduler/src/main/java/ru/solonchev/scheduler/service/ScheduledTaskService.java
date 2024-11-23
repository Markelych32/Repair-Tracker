package ru.solonchev.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.solonchev.scheduler.config.dto.AnalyseResponseDto;
import ru.solonchev.scheduler.repository.UserRepository;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final UserRepository userRepository;
    private final UserAnalyseService userAnalyseService;
    private final EmailSendingKafkaService emailSendingKafkaService;

    @Scheduled(cron = "0 0 0 * * *", zone = "${scheduler.time-zone}")
    public void scheduledCronExpressionTask() {
        userRepository.findAll().forEach(user -> {
            AnalyseResponseDto response = userAnalyseService.analyse(user);
            emailSendingKafkaService.sendEmailMessage(response);
        });
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void testScheduledMethod() {
        log.info("Scheduler is checking users.");
        userRepository.findAll().forEach(user -> {
            AnalyseResponseDto response = userAnalyseService.analyse(user);
            emailSendingKafkaService.sendEmailMessage(response);
        });
    }
}
