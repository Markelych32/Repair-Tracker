package ru.solonchev.backend.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "User save dto", description = "Сущность для добавления нового пользователя")
public record UserSaveDto(
        @Schema(name = "first_name", description = "Имя пользователя")
        @Size(max = 32)
        @JsonProperty("first_name")
        String firstName,

        @Schema(name = "last_name", description = "Фамилия пользователя")
        @Size(max = 32)
        @JsonProperty("last_name")
        String lastName,

        @Schema(name = "email", description = "email пользователя")
        @JsonProperty("email")
        String email,

        @Schema(name = "password", description = "Пароль пользователя")
        @JsonProperty("password")
        String password
) {
}
