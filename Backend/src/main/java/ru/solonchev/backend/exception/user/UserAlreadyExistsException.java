package ru.solonchev.backend.exception.user;

import lombok.Getter;
import ru.solonchev.backend.exception.ErrorCode;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final String code;

    public UserAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
