package ru.yandex.practikum.Courier;

import java.sql.Timestamp;

public class CourierGenerator {
    public static Courier getDefault() {
        //Timestamp is used to make unique data to avoid accidental 409 errors
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Courier("rts_" + timestamp.getTime(), "12390", "rts");
    }
}