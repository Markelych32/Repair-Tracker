package ru.solonchev.backend.dto.task.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonPropertyOrder({"size", "types"})
public record ListTitleTypeDto(
        @Schema(name = "size", description = "Количество элементов")
        @JsonProperty("size")
        String size,
        @Schema(name = "types", description = "Список типов задач")
        @JsonProperty("types")
        List<TitleTypeDto> types
) {
}
