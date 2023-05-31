package ru.yandex.yandexlavka.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public record ValidationErrorResponse(List<Violation> violations) {

}