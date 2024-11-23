package ru.solonchev.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.solonchev.backend.dto.Error;
import ru.solonchev.backend.exception.task.TaskNotFoundException;
import ru.solonchev.backend.exception.user.UserAlreadyExistsException;
import ru.solonchev.backend.exception.user.UserNotFoundException;

@RestControllerAdvice
public class BackendExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> userNotFound(UserNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getCode(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Error> userNotFound(TaskNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getCode(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> userAlreadyExists(UserAlreadyExistsException exception) {
        return new ResponseEntity<>(new Error(exception.getCode(), exception.getMessage()), HttpStatus.CONFLICT);
    }
}
