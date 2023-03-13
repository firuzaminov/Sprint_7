package ru.yandex.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.courier.Courier;
import ru.yandex.practikum.courier.CourierClient;
import ru.yandex.practikum.courier.CourierGenerator;
import ru.yandex.practikum.courier.Credential;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {

    private final static String ERROR_MESSAGE_400 = "Учетная запись не найдена";
    private final static String ERROR_MESSAGE_400_REQUIRED = "Недостаточно данных для входа";
    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();
    }


    @Test
    @DisplayName("Login courier")
    public void courierCanAuthorize() {
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credential.from(courier));
        int actualStatusCode = responseLogin.extract().statusCode();
        id = responseLogin.extract().path("id");
        assertEquals(SC_OK, actualStatusCode);
        assertNotNull(id);
    }

    @Test
    @DisplayName("Login courier with wrong login")
    public void courierCanNotAuthorizeWithWrongLogin() {
        courierClient.create(courier);
        ValidatableResponse responseCorrectLogin = courierClient.login(Credential.from(courier));
        id = responseCorrectLogin.extract().path("id");
        courier.setLogin("*&%%12321");
        ValidatableResponse responseWrongLogin = courierClient.login(Credential.from(courier));
        int actualStatusCode = responseWrongLogin.extract().statusCode();
        assertEquals(SC_NOT_FOUND, actualStatusCode);
        String actualMessage = responseWrongLogin.extract().path("message");
        assertEquals(ERROR_MESSAGE_400, actualMessage);
    }

    @Test
    @DisplayName("Login courier with wrong password")
    public void courierCanNotAuthorizeWithWrongPassword() {
        courierClient.create(courier);
        ValidatableResponse responseCorrectLogin = courierClient.login(Credential.from(courier));
        id = responseCorrectLogin.extract().path("id");
        courier.setPassword("*&%%12321");
        ValidatableResponse responseWrongLogin = courierClient.login(Credential.from(courier));
        int actualStatusCode = responseWrongLogin.extract().statusCode();
        assertEquals(SC_NOT_FOUND, actualStatusCode);
        String actualMessage = responseWrongLogin.extract().path("message");
        assertEquals(ERROR_MESSAGE_400, actualMessage);
    }

    @Test
    @DisplayName("Login courier without required field login")
    public void courierCanNotAuthorizeWithoutLogin() {
        courierClient.create(courier);
        ValidatableResponse responseCorrectLogin = courierClient.login(Credential.from(courier));
        id = responseCorrectLogin.extract().path("id");
        courier.setLogin(null);
        ValidatableResponse responseWrongLogin = courierClient.login(Credential.from(courier));
        int actualStatusCode = responseWrongLogin.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        String actualMessage = responseWrongLogin.extract().path("message");
        assertEquals(ERROR_MESSAGE_400_REQUIRED, actualMessage);
    }

    @Test
    @DisplayName("Login courier without required field password")
    public void courierCanNotAuthorizeWithoutPassword() {
        courierClient.create(courier);
        ValidatableResponse responseCorrectLogin = courierClient.login(Credential.from(courier));
        id = responseCorrectLogin.extract().path("id");
        courier.setPassword("");
        ValidatableResponse responseWrongLogin = courierClient.login(Credential.from(courier));
        int actualStatusCode = responseWrongLogin.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        String actualMessage = responseWrongLogin.extract().path("message");
        assertEquals(ERROR_MESSAGE_400_REQUIRED, actualMessage);
    }
}