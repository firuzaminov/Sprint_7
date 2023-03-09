package ru.yandex.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private OrderClient orderClient;
    private Order order;
    private int track;
    private int statusCode;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseDelete = orderClient.cancel(track);
        int actualStatusCode = responseDelete.extract().statusCode();
        assertEquals(SC_OK,actualStatusCode);
    }

    public OrderCreationTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {OrderGenerator.colourBlackAndGrey(),SC_OK},
                {OrderGenerator.colourBlack(), SC_OK},
                {OrderGenerator.colourGrey(), SC_OK},
                {OrderGenerator.colourNull(), SC_OK},
                {OrderGenerator.colourEmpty(), SC_OK}
        };
    }

    @Test
    @DisplayName("Create order")
    public void orderCreatedPositive() {
        ValidatableResponse responseCreate = orderClient.create(order);
        int actualStatusCode = responseCreate.extract().statusCode();
        assertEquals(SC_CREATED,actualStatusCode);
        track = responseCreate.extract().path("track");
        assertNotNull(track);
    }
}