package ru.yandex.yandexlavka.entities.interfaces;

import ru.yandex.yandexlavka.utils.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface HasSchedule {
    List<Pair<LocalTime, LocalTime>> getPairSchedule();

    void setPairSchedule();
}
