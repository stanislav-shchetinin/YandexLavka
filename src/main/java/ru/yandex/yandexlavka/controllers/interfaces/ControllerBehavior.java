package ru.yandex.yandexlavka.controllers.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.yandexlavka.rate.limiter.RateLimiterException;

public interface ControllerBehavior {
    @ExceptionHandler(RateLimiterException.class)
    default ResponseEntity<String> handleException(RateLimiterException e) {
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }
}
