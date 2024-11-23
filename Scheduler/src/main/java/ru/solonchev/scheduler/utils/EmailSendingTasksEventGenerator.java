package ru.solonchev.scheduler.utils;

import java.util.List;
import ru.solonchev.event.EmailSendingTasksEvent;
import ru.solonchev.scheduler.config.dto.AnalyseResponseDto;


public final class EmailSendingTasksEventGenerator {

    private static final String HEADER_OF_LETTER = "Repair Tracker: Daily Analyse";

    private EmailSendingTasksEventGenerator() {

    }

    public static EmailSendingTasksEvent generate(AnalyseResponseDto dto) {
        return new EmailSendingTasksEvent(
                dto.getEmail(),
                generateHeaderOfLetter(),
                generateFinished(dto),
                generateActive(dto)
        );
    }

    public static String generateHeaderOfLetter() {
        return HEADER_OF_LETTER;
    }

    public static List<String> generateFinished(AnalyseResponseDto dto) {
        return dto.getFinished().titles();
    }

    public static List<String> generateActive(AnalyseResponseDto dto) {
        return dto.getActive().titles();
    }
}
