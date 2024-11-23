package ru.solonchev.backend.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(name = "User Login Response dto", description = "Ответ на вход пользователя в систему")
public record UserLoginResponseDto(
        @Schema(name = "user_id", description = "Идентификатор пользователя")
        @JsonProperty("user_id")
        @Min(0)
        @Max(Integer.MAX_VALUE)
        Integer userId,

        @Schema(name = "email", description = "email пользователя")
        @JsonProperty("email")
        String email,

        @Schema(name = "token", description = "jwt токен для авторизации")
        @JsonProperty("token")
        String token
) {
}
