package ru.yandex.yandexlavka.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.controllers.interfaces.ControllerBehavior;
import ru.yandex.yandexlavka.entities.courier.Courier;
import ru.yandex.yandexlavka.entities.courier.MessageCourierInfo;
import ru.yandex.yandexlavka.entities.courier.ModeOfTransport;
import ru.yandex.yandexlavka.entities.order.Order;
import ru.yandex.yandexlavka.entities.order.complete.CompleteOrder;
import ru.yandex.yandexlavka.rate.limiter.RateLimited;
import ru.yandex.yandexlavka.repo.CompleteOrderRepo;
import ru.yandex.yandexlavka.repo.CourierRepo;
import ru.yandex.yandexlavka.repo.OrderRepo;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("couriers/meta-info")
@Valid
public class CourierInfoController implements ControllerBehavior {

    private final CompleteOrderRepo completeOrderRepo;
    private final OrderRepo orderRepo;
    private final CourierRepo courierRepo;

    @Autowired
    public CourierInfoController(CompleteOrderRepo completeOrderRepo, OrderRepo orderRepo, CourierRepo courierRepo) {
        this.completeOrderRepo = completeOrderRepo;
        this.orderRepo = orderRepo;
        this.courierRepo = courierRepo;
    }

    @RateLimited
    @ResponseBody
    @GetMapping("{id}")
    public ResponseEntity<?> getCourierInfo(@PathVariable("id") Courier courier,
                                            @RequestParam(value = "start_date")
                                            LocalDate start_date,
                                            @RequestParam(value = "end_date")
                                            LocalDate end_date){

        Double wages = 0.;
        Double rating;
        Long countOrders = 0L;
        Long courierId = courier.getId();

        for (CompleteOrder completeOrder : completeOrderRepo.findAll()){
            LocalDate completeOrderDate = completeOrder.getDate();
            if (completeOrder.getCourierId().equals(courierId)
                && completeOrderDate.compareTo(start_date) >= 0
                && completeOrderDate.compareTo(end_date) < 0){

                Optional<Order> order = orderRepo.findById(completeOrder.getId());
                if (order.isPresent()){
                    wages += order.get().getPrice();
                    countOrders++;
                }

            }
        }

        if (countOrders > 0){
            wages *= getCForWages(courier);
            rating = countOrders*1.0 / getCountHoursBetweenDates(start_date, end_date) * getCForRating(courier);
            return new ResponseEntity<>(new MessageCourierInfo(wages, rating), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private Integer getCForWages(Courier courier){
        final Integer COEFFICIENT_FOOD = 2;
        final Integer COEFFICIENT_BICYCLE = 3;
        final Integer COEFFICIENT_CAR = 4;
        if (courier.getModeOfTransport().equals(ModeOfTransport.FOOD)){
            return COEFFICIENT_FOOD;
        } else if (courier.getModeOfTransport().equals(ModeOfTransport.BICYCLE)){
            return COEFFICIENT_BICYCLE;
        } else {
            return COEFFICIENT_CAR;
        }
    }

    private Integer getCForRating(Courier courier){
        return 5 - getCForWages(courier);
    }

    private Long getCountHoursBetweenDates(LocalDate localDate1, LocalDate localDate2){
        final int COUNT_HOURS_IN_DAY = 24;
        return DAYS.between(localDate1, localDate2) * COUNT_HOURS_IN_DAY;
    }

}
