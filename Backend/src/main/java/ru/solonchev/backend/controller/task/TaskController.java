package ru.solonchev.backend.controller.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.solonchev.backend.dto.Error;
import ru.solonchev.backend.dto.task.request.TaskSaveDto;
import ru.solonchev.backend.dto.task.response.ListTitleTypeDto;
import ru.solonchev.backend.dto.task.response.TaskResponseDto;

import java.util.UUID;

@Tag(name = "Task Controller", description = "Api для работы с задачами")
@RequestMapping("/api/v1/tasks")
@Validated
@SecurityRequirement(name = "JWT")
public interface TaskController {

    @Operation(
            summary = "Добавление новой задачи",
            description = "Добавляет новую задачу для конкретного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно добавлена",
                            content = @Content(schema = @Schema(implementation = TaskResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Текущий пользователь не найден",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> addTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "json запрос для добавления задачи", required = true)
            @Schema(name = "Task save dto", implementation = TaskSaveDto.class)
            @RequestBody @Valid
            TaskSaveDto request);

    @Operation(
            summary = "Обновление задачи",
            description = "Изменяет уже существующую задачу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное изменение задачи",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача не найдена",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PutMapping
    ResponseEntity<?> updateTask(
            @Parameter(name = "taskSaveDto", description = "Тело обновленной задачи")
            @RequestBody TaskSaveDto request);

    @Operation(
            summary = "Удаление задачи",
            description = "Удаление задачи у пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное удаление задачи",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача не найдена",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @DeleteMapping
    ResponseEntity<?> deleteTask(
            @Parameter(name = "taskId", description = "Идентификатор пользователя", required = true, in = ParameterIn.HEADER)
            @RequestHeader(name = "taskId") UUID taskId
    );

    @Operation(
            summary = "Получение списка типов задач.",
            description = "Возвращает список типов возможных задач.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение типов задач",
                            content = @Content(schema = @Schema(implementation = ListTitleTypeDto.class))
                    )
            }
    )
    @GetMapping("/types")
    ResponseEntity<?> getTypesTitles();
}
