package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerBehavior;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerWithArguments;
import ru.yandex.yandexlavka.entities.interfaces.HasSchedule;
import ru.yandex.yandexlavka.rate.limiter.RateLimited;

import java.util.List;

import static java.lang.Math.min;

public class AbstractEntityController<T> implements ControllerBehavior, ControllerWithArguments {

    private final JpaRepository<T, Long> repo;

    @Autowired
    public AbstractEntityController(JpaRepository<T, Long> repo) {
        this.repo = repo;
    }

    @RateLimited
    @GetMapping
    public List<T> getAll(@RequestParam(value = "offset", defaultValue = "0")
                                        Integer offset,
                                        @RequestParam(value = "limit", defaultValue = "1")
                                        Integer limit){
        List<T> list = repo.findAll();
        return list.subList(
                offset,
                min(limit, list.size())
        );

    }

    @RateLimited
    @GetMapping("{id}")
    public T getOne(@Valid @PathVariable("id") T record){
        return record;
    }

    @RateLimited
    @PostMapping
    public ResponseEntity<?> addOne(@Valid @RequestBody T record){
        if (HasSchedule.class.isAssignableFrom(record.getClass())){
            ((HasSchedule) record).setPairSchedule();

            boolean matched = ((HasSchedule) record).getPairSchedule().stream().anyMatch(
                    pair -> pair.getLeft().compareTo(pair.getRight()) > 0
                    );

            if (matched){
                return new ResponseEntity<>("The left time is greater than the right", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(repo.save(record), HttpStatus.OK);
        }
        return new ResponseEntity<>("Not HasSchedule", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
