package ru.solonchev.backend.exception.user;

import lombok.Getter;
import ru.solonchev.backend.exception.ErrorCode;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String code;

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
