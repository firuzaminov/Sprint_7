package ru.yandex.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.order.Order;
import ru.yandex.practikum.order.OrderClient;
import ru.yandex.practikum.order.OrderGenerator;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    private OrderClient orderClient;
    private Order order;
    private int track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.colourBlackAndGrey();
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseDelete = orderClient.cancel(track);
        int actualStatusCode = responseDelete.extract().statusCode();
        assertEquals(SC_OK, actualStatusCode);
    }

    @Test
    @DisplayName("Get orders")
    public void getOrderList() {
        ValidatableResponse responseCreate = orderClient.create(order);
        track = responseCreate.extract().path("track");
        ValidatableResponse responseGetOrders = orderClient.orderList();
        int statusCodeGetList = responseGetOrders.extract().statusCode();
        assertEquals(SC_OK, statusCodeGetList);
        ArrayList<String> listOrders = responseGetOrders.extract().path("orders");
        assertNotNull(listOrders);
    }
}