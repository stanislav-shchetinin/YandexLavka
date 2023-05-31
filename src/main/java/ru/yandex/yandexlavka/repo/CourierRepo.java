package ru.yandex.yandexlavka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.yandexlavka.entities.courier.Courier;

public interface CourierRepo extends JpaRepository<Courier, Long> {
}
