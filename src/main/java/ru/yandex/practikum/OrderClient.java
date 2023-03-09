package ru.yandex.practikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static ru.yandex.practikum.Client.getSpec;

public class OrderClient extends Client {
    private static final String PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step ("Отменить заказ")
    public ValidatableResponse cancel(int track) {
        return given()
                .spec(getSpec())
                .when()
                .put(PATH+"/cancel?track="+track)
                .then();
    }

    @Step ("Получение списка заказов")
    public ValidatableResponse orderList() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }
}