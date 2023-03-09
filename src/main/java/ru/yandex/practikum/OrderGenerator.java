package ru.yandex.practikum;

import java.util.List;

public class OrderGenerator {

    public static Order colourBlackAndGrey() {
        return new Order("Фируз","Аминов","ул. Острякова, д. 17","3","+7 978 983 43 35",5,"2022-05-17","Комментарий к заказу", List.of("BLACK", "GREY"));
    }

    public static Order colourBlack() {
        return new Order("Фируз","Аминов","ул. Острякова, д. 17","3","++7 978 983 43 35",5,"2022-05-17","Комментарий к заказу",List.of("BLACK"));
    }

    public static Order colourGrey() {
        return new Order("Фируз","Аминов","ул. Острякова, д. 17","3","+7 978 983 43 35",5,"2022-05-17","Комментарий к заказу",List.of("GREY"));
    }

    public static Order colourNull() {
        return new Order("Фируз","Аминов","ул. Острякова, д. 17","3","+7 978 983 43 35",5,"2022-05-17","Комментарий к заказу",null);
    }

    public static Order colourEmpty() {
        return new Order("Фируз","Аминов","ул. Острякова, д. 17","3","+7 978 983 43 35",5,"2022-05-17","Комментарий к заказу",List.of(""));
    }
}