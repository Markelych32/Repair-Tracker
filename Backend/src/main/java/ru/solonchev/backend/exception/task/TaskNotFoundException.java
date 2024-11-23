package ru.solonchev.backend.exception.task;

import lombok.Getter;
import ru.solonchev.backend.exception.ErrorCode;

@Getter
public class TaskNotFoundException extends RuntimeException {
    private final String code;

    public TaskNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
