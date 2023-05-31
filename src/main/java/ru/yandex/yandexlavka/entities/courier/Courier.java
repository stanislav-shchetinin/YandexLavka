package ru.yandex.yandexlavka.entities.courier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.yandex.yandexlavka.entities.AbstractEntity;
import ru.yandex.yandexlavka.entities.interfaces.EntityDB;
import ru.yandex.yandexlavka.entities.interfaces.HasSchedule;
import ru.yandex.yandexlavka.utils.Pair;

import java.time.LocalTime;
import java.util.*;

@Entity
@Table
@ToString(of = {"id", "name", "area", "modeOfTransport", "workSchedule"})
@JsonIgnoreProperties({"pairSchedule"})
@EqualsAndHashCode(of = {"id"})
public class Courier implements EntityDB, HasSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @NotNull
    @Min(value = 1, message = "The district number must be greater than or equal to 1")
    @Max(value = 999999999, message = "The area number is too large")
    private Integer area;
    @NotNull(message = "The mode of movement cannot be null")
    private ModeOfTransport modeOfTransport;
    private List<@Pattern(
            regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]-([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Does not match the HH:MM-HH:MM format"
    )String> workSchedule;
    @Transient
    private List<Pair<LocalTime, LocalTime>> pairSchedule;

    public Courier() {}
    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public ModeOfTransport getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(ModeOfTransport modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public List<String> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<String> workSchedule) {
        this.workSchedule = workSchedule;
    }

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
