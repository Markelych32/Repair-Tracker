package ru.solonchev.backend.dto.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность для входа уже зарегистрированного пользователя")
public record UserLoginDto(
        @Schema(name = "email", description = "email пользователя")
        @JsonProperty("email")
        String email,

        @Schema(name = "password", description = "Пароль пользователя для входа")
        @JsonProperty("password")
        String password
) {
}
