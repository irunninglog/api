package com.irunninglog.math;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ApiMathTest {

    @Test
    public void miles() {
        assertEquals(new BigDecimal("3.4999965530615234375"), ApiMath.miles(BigDecimal.valueOf(5632.7F)));
        assertEquals(new BigDecimal("530.0229386045"), ApiMath.miles(BigDecimal.valueOf(852989.5F)));
        assertEquals(new BigDecimal("2222.0118220075"), ApiMath.miles(BigDecimal.valueOf(3575982.4F)));
        assertEquals(new BigDecimal("1524.99030084225"), ApiMath.miles(BigDecimal.valueOf(2454234.7F)));
        assertEquals(new BigDecimal("1834.9877878025"), ApiMath.miles(BigDecimal.valueOf(2953127.4F)));
        assertEquals(new BigDecimal("1499.99006002825"), ApiMath.miles(BigDecimal.valueOf(2414000.8F)));
        assertEquals(new BigDecimal("1205.99209960425"), ApiMath.miles(BigDecimal.valueOf(1940856.8F)));
    }

    @Test
    public void round() {
        assertEquals(new BigDecimal("3.5"), ApiMath.round(new BigDecimal("3.4999965530615234375")));
        assertEquals(new BigDecimal("530.0"), ApiMath.round(new BigDecimal("530.0229386045")));
        assertEquals(new BigDecimal("2222.0"), ApiMath.round(new BigDecimal("2222.0118220075")));
        assertEquals(new BigDecimal("1525.0"), ApiMath.round(new BigDecimal("1524.99030084225")));
        assertEquals(new BigDecimal("1835.0"), ApiMath.round(new BigDecimal("1834.9877878025")));
        assertEquals(new BigDecimal("1500.0"), ApiMath.round(new BigDecimal("1499.99006002825")));
        assertEquals(new BigDecimal("1206.0"), ApiMath.round(new BigDecimal("1205.99209960425")));
    }

    @Test
    public void floor() {
        assertEquals(new BigDecimal(1), ApiMath.floor(BigDecimal.valueOf(1.9999999999)));
        assertEquals(new BigDecimal(1), ApiMath.floor(BigDecimal.valueOf(1.0000000001)));
        assertEquals(new BigDecimal(-1), ApiMath.floor(BigDecimal.valueOf(-.8)));
    }

    @Test
    public void format() {
        assertEquals("1,000", ApiMath.format(BigDecimal.valueOf(1000), ApiMath.FORMAT_FORMATTED));
        assertEquals("1000", ApiMath.format(BigDecimal.valueOf(1000), ApiMath.FORMAT_PLAIN));
        assertEquals("1,000 mi", ApiMath.format(BigDecimal.valueOf(1000), ApiMath.FORMAT_FORMATTED_MILEAGE));
    }

}
