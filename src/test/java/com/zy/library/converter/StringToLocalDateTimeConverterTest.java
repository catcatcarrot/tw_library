package com.zy.library.converter;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StringToLocalDateTimeConverterTest {

    private StringToLocalDateTimeConverter timeConverter = new StringToLocalDateTimeConverter();

    @Test
    void convert() {
        String inputDateString = "2020-08-08 00:00:00";
        LocalDateTime expectedOutput =
                LocalDateTime.of(2020,8,8,0,0,0);

        assertEquals(expectedOutput, timeConverter.convert(inputDateString));
    }
}