package ru.solonchev.backend.dto.task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {
    @Schema(name = "id", description = "Идентификатор задачи")
    @JsonProperty("id")
    private String id;
    @Schema(name = "title", description = "Заголовок задачи")
    @JsonProperty("title")
    private String title;
    @Schema(name = "payload", description = "Тело задачи")
    @JsonProperty("payload")
    private String payload;
    @Schema(name = "is_done", description = "Закончена ли задача")
    @JsonProperty("is_done")
    private boolean isDone;
}
