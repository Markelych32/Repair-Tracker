package ru.solonchev.backend.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.solonchev.backend.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND_BY_ID("Пользователь не найден по идентификатору."),
    USER_WITH_SUCH_EMAIL_ALREADY_EXISTS("Пользователь с таким email уже существует."),
    USER_NOT_FOUND_BY_EMAIL("Пользователь не найден по email.");

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
