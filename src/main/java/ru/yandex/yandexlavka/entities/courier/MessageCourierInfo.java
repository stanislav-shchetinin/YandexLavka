package ru.yandex.yandexlavka.entities.courier;

import lombok.ToString;
import org.springframework.web.bind.annotation.ResponseBody;

@ToString(of = {"wages", "rating"})
public class MessageCourierInfo {
    private Double wages;
    private Double rating;

    public void setWages(Double wages) {
        this.wages = wages;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getWages() {
        return wages;
    }

    public Double getRating() {
        return rating;
    }

    public MessageCourierInfo(Double wages, Double rating) {
        this.wages = wages;
        this.rating = rating;
    }
    public MessageCourierInfo(){}
}
