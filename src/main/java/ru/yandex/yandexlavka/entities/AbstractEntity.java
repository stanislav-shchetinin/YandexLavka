package ru.yandex.yandexlavka.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.yandexlavka.entities.courier.ModeOfTransport;
import ru.yandex.yandexlavka.entities.interfaces.EntityDB;
import ru.yandex.yandexlavka.entities.interfaces.HasSchedule;
import ru.yandex.yandexlavka.utils.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AbstractEntity implements HasSchedule {

    private List<Pair<LocalTime, LocalTime>> pairSchedule;
    private List<@Pattern(
            regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]-([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Does not match the HH:MM-HH:MM format"
    )String> workSchedule;
    @Override
    public List<Pair<LocalTime, LocalTime>> getPairSchedule() {
        return pairSchedule;
    }

    @Override
    public void setPairSchedule() {
        this.pairSchedule = new ArrayList<>();
        workSchedule.stream().map(x -> x.split("-")).forEach(
                x -> this.pairSchedule.add(
                        new Pair<>(LocalTime.parse(x[0]), LocalTime.parse(x[1]))
                )
        );
    }

}
