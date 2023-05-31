package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.rate.limiter.RateLimited;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerBehavior;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerWithArguments;
import ru.yandex.yandexlavka.entities.order.Order;
import ru.yandex.yandexlavka.repo.OrderRepo;

import java.util.List;

import static java.lang.Math.min;

@RestController
@RequestMapping("orders")
@Valid
public class OrdersController extends AbstractEntityController<Order> {
    private final OrderRepo orderRepo;

    public OrdersController(OrderRepo orderRepo) {
        super(orderRepo);
        this.orderRepo = orderRepo;
    }

}
