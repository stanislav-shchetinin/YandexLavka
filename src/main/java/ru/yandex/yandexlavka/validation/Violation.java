package ru.yandex.yandexlavka.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record Violation(String fieldName, String message) {

}