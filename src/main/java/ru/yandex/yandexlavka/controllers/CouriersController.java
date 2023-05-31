package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.rate.limiter.RateLimited;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerBehavior;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerWithArguments;
import ru.yandex.yandexlavka.entities.courier.Courier;
import ru.yandex.yandexlavka.repo.CourierRepo;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@RestController
@RequestMapping("couriers")
@Valid
public class CouriersController extends AbstractEntityController<Courier> {

    private final CourierRepo courierRepo;

    @Autowired
    public CouriersController(CourierRepo courierRepo) {
        super(courierRepo);
        this.courierRepo = courierRepo;
    }

}
