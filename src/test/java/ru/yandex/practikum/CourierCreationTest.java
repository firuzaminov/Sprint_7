package ru.yandex.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.Courier.Courier;
import ru.yandex.practikum.Courier.CourierClient;
import ru.yandex.practikum.Courier.CourierGenerator;
import ru.yandex.practikum.Courier.Credential;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CourierCreationTest {
    private final static String ERROR_MESSAGE_400 = "Недостаточно данных для создания учетной записи";
    private final static String ERROR_MESSAGE_409 = "Этот логин уже используется. Попробуйте другой.";
    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseDelete = courierClient.delete(id);
    }

    @Test
    @DisplayName("Courier creation")
    public void courierCreatedPositive() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        int actualStatusCode = responseCreate.extract().statusCode();
        id = responseLogin.extract().path("id");
        Boolean isCourierCreated = responseCreate.extract().path("ok");
        assertEquals(SC_CREATED, actualStatusCode);
        assertTrue(isCourierCreated);
    }

    @Test
    @DisplayName("Courier creation with credits that already used")
    public void sameCourierCreatedNegative() {
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        id = responseLogin.extract().path("id");
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCode = responseCreate.extract().path("code");
        String message = responseCreate.extract().path("message");
        assertEquals(SC_CONFLICT, statusCode);
        assertEquals(ERROR_MESSAGE_409, message);
    }

    @Test
    @DisplayName("New courier can't be created without login")
    public void courierShouldHaveLogin() {
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        id = responseLogin.extract().path("id");
        courier.setLogin(null);
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCode = responseCreate.extract().path("code");
        String message = responseCreate.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals(ERROR_MESSAGE_400, message);
    }

    @Test
    @DisplayName("New courier can't be created without password")
    public void courierShouldHavePassword() {
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        id = responseLogin.extract().path("id");
        courier.setPassword(null);
        ValidatableResponse responseCreate = courierClient.create(courier);
        int statusCode = responseCreate.extract().path("code");
        String message = responseCreate.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals(ERROR_MESSAGE_400, message);
    }
}