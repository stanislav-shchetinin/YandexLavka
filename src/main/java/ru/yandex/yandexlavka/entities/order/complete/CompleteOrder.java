package ru.yandex.yandexlavka.entities.order.complete;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.yandex.yandexlavka.entities.interfaces.EntityDB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="complete_order")
@ToString(of = {"id", "courierId", "time"})
@EqualsAndHashCode(of = {"id"})
public class CompleteOrder implements EntityDB {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private Long courierId;
    @NotNull
    private LocalTime time;
    @NotNull
    private LocalDate date;
    @Override
    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
