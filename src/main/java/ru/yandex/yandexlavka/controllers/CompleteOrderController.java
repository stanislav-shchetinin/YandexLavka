package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.rate.limiter.RateLimited;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerBehavior;
import ru.yandex.yandexlavka.entities.order.complete.CompleteOrder;
import ru.yandex.yandexlavka.repo.CompleteOrderRepo;
import ru.yandex.yandexlavka.repo.CourierRepo;
import ru.yandex.yandexlavka.repo.OrderRepo;

import java.util.List;

import static java.lang.Math.min;

@RestController
@RequestMapping("orders/complete")
@Valid
public class CompleteOrderController implements ControllerBehavior {
    private final CompleteOrderRepo completeOrderRepo;
    private final OrderRepo orderRepo;
    private final CourierRepo courierRepo;

    @Autowired
    public CompleteOrderController(CompleteOrderRepo completeOrderRepo, OrderRepo orderRepo, CourierRepo courierRepo) {
        this.completeOrderRepo = completeOrderRepo;
        this.orderRepo = orderRepo;
        this.courierRepo = courierRepo;
    }

    @RateLimited
    @GetMapping
    public List<CompleteOrder> getAllOrders(){
        return completeOrderRepo.findAll();
    }

    @RateLimited
    @PostMapping
    public ResponseEntity<?> addOneCompleteOrder(@Valid @RequestBody CompleteOrder completeOrder){
        Long orderId = completeOrder.getId();
        Long courierId = completeOrder.getCourierId();

        if (orderRepo.existsById(orderId) && courierRepo.existsById(courierId)){
            completeOrderRepo.save(completeOrder);
            return new ResponseEntity<>(orderId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error in the order or courier ID", HttpStatus.BAD_REQUEST);
        }

    }

}
