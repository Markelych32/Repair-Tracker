package ru.solonchev.scheduler.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyseResponseDto {
        @JsonProperty("email")
        private String email;
        @JsonProperty("finished")
        private DoneTasksDto finished;
        @JsonProperty("active")
        private NotDoneTasksDto active;
}
