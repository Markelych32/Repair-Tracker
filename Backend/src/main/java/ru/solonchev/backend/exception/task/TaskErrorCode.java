package ru.solonchev.backend.exception.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.solonchev.backend.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public enum TaskErrorCode implements ErrorCode {
    TASK_NOT_FOUND_BY_ID("Задача не найдена по идентификатору.");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
