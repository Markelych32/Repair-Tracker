package ru.solonchev.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Ошибка")
public class Error {

    @Schema(name = "code", description = "Код ошибки")
    @Size(max = 255)
    private String code;

    @Schema(name = "message", description = "Описание ошибки")
    @Size(max = 1024)
    private String message;
}
