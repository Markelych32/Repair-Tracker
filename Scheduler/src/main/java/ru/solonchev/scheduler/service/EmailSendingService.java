package ru.solonchev.scheduler.service;

import ru.solonchev.scheduler.config.dto.AnalyseResponseDto;

public interface EmailSendingService {
    void sendEmailMessage(AnalyseResponseDto dto);
}
