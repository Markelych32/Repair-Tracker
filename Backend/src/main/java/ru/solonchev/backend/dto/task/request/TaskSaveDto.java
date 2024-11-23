package ru.solonchev.backend.dto.task.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(name = "Task save dto", description = "Новая задача для пользователя")
public record TaskSaveDto(
        @Schema(name = "task_id", description = "Идентификатор пользователя")
        @JsonProperty("task_id")
        UUID taskId,
        @Schema(name = "user_id", description = "Идентификатор пользователя")
        @Min(0)
        @Max(Integer.MAX_VALUE)
        @JsonProperty("user_id")
        Integer userId,

        @Schema(name = "title", description = "Заголовок задачи")
        @Size(max = 128)
        @JsonProperty("title")
        String title,

        @Schema(name = "payLoad", description = "Тело задачи", nullable = true)
        @Size(max = 4096)
        @JsonProperty("payload")
        String payload,

        @Schema(name = "is_done", description = "Завершена ли задача")
        @JsonProperty("is_done")
        boolean isDone
) {
}
