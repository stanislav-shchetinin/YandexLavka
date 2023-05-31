package ru.yandex.yandexlavka.controllers.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ControllerWithArguments {



    @ExceptionHandler(IllegalArgumentException.class)
    default ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>("Error in the limit or/and offset argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    default ResponseEntity<String> handleException(MissingPathVariableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
