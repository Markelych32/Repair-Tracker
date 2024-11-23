package ru.solonchev.scheduler.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record DoneTasksDto(
        @JsonProperty("length")
        int length,
        @JsonProperty("titles")
        List<String> titles
) {
}
