package ru.yandex.yandexlavka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.yandexlavka.entities.order.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
