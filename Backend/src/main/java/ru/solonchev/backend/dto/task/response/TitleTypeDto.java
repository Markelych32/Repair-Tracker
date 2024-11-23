package ru.solonchev.backend.dto.task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonPropertyOrder({"id", "name"})
public record TitleTypeDto(
        @Schema(name = "id", description = "Идентификатор типа задач")
        @JsonProperty("id")
        String id,
        @Schema(name = "name", description = "Название типа задачи")
        @JsonProperty("name")
        String name
) {
}
