package ru.yandex.yandexlavka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.yandexlavka.entities.order.complete.CompleteOrder;

public interface CompleteOrderRepo extends JpaRepository<CompleteOrder, Long> {
}
