package ru.yandex.yandexlavka.entities.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.yandex.yandexlavka.entities.interfaces.EntityDB;
import ru.yandex.yandexlavka.entities.interfaces.HasSchedule;
import ru.yandex.yandexlavka.utils.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="order_lavka")
@ToString(of = {"id", "area", "workSchedule"})
@JsonIgnoreProperties({"pairSchedule"})
@EqualsAndHashCode(of = {"id"})
public class Order implements EntityDB, HasSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 1, message = "The district number must be greater than or equal to 1")
    @Max(value = 999999999, message = "The area number is too large")
    private Integer area;

    private List<@Pattern(
            regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]-([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Does not match the HH:MM-HH:MM format"
    )String> workSchedule;

    @Transient
    private List<Pair<LocalTime, LocalTime>> pairSchedule;
    @Positive
    private Double price;
    @Positive
    private Double weight;

    @Override
    public Long getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public List<String> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<String> workSchedule) {
        this.workSchedule = workSchedule;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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
