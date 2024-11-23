package ru.solonchev.backend.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.solonchev.backend.dto.Error;
import ru.solonchev.backend.dto.user.request.UserLoginDto;
import ru.solonchev.backend.dto.user.request.UserSaveDto;
import ru.solonchev.backend.dto.user.response.UserLoginResponseDto;


@Tag(name = "Security Controller", description = "Api для регистрации/входа в аккаунт")
@RequestMapping("/auth")
public interface SecurityController {

    @Operation(
            summary = "Вход в аккаунт",
            description = "Вход в аккаунт через email и password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный вход в аккаунт",
                            content = @Content(schema = @Schema(implementation = UserLoginResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка при авторизации",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    @PostMapping("/login")
    ResponseEntity<?> login(
            @Parameter(name = "Тело запроса для входа", schema = @Schema(implementation = UserLoginDto.class))
            @RequestBody UserLoginDto request
    );

    @Operation(
            summary = "Регистрация пользователя",
            description = "Идентификация пользователя в системе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная регистрация пользователя",
                            content = @Content(schema = @Schema(
                                    implementation = String.class,
                                    example = "User has been registered.")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email уже занят",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PostMapping("/register")
    ResponseEntity<?> register(
            @Parameter(name = "Тело запроса для входа", schema = @Schema(implementation = UserSaveDto.class))
            @RequestBody UserSaveDto request
    );
}
