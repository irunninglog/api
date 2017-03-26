package com.irunninglog.spring.jpa;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DateConverterTest {

    private final DateConverter converter = new DateConverter();

    @Test
    public void convertToDatabaseColumn() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNotNull(converter.convertToDatabaseColumn(LocalDate.now()));
    }

    @Test
    public void convertToEntityAttribute() {
        assertNull(converter.convertToEntityAttribute(null));
        assertNotNull(converter.convertToEntityAttribute(new Date(System.currentTimeMillis())));
    }

}
