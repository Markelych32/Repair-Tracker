package ru.solonchev.backend.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.solonchev.backend.dto.Error;
import ru.solonchev.backend.dto.user.response.UserTasksDto;

@Tag(name = "User Controller", description = "Api для работы с пользователем")
@RequestMapping("/api/v1/users")
@Validated
@SecurityRequirement(name = "JWT")
public interface UserController {

    @Operation(
            summary = "Удаление пользователя",
            description = "Удаление пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное удаление пользователя",
                            content = @Content(schema = @Schema(
                                    implementation = String.class,
                                    example = "User deleted successfully")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден по id",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUser(
            @Parameter(name = "userId", description = "Идентификатор пользователя")
            @PathVariable("userId") Integer userId
    );

    @Operation(
            summary = "Получение задач пользователя",
            description = "Получение всех задач пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение задач пользователя",
                            content = @Content(schema = @Schema(implementation = UserTasksDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден по id",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GetMapping("/{userId}/tasks")
    ResponseEntity<?> getTasksOfUser(
            @Parameter(name = "userId", description = "Идентификатор пользователя")
            @PathVariable Integer userId
    );
}
