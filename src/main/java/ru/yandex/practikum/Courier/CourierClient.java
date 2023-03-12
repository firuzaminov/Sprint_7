package ru.yandex.practikum.Courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practikum.Client.Client;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String PATH = "api/v1/courier";
    private static final String PATH_LOGIN = "api/v1/courier/login";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(Credential credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH + "/" + id)
                .then();
    }
}