package com.irunninglog.math;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ApiMathTest {

    @Test
    public void metersToMiles1() {
        assertEquals(new BigDecimal("3.5"), ApiMath.metersToMiles(BigDecimal.valueOf(5632.7F)));
    }

    @Test
    public void metersToMiles2() {
        assertEquals(new BigDecimal("530.0"), ApiMath.metersToMiles(BigDecimal.valueOf(852989.5F)));
    }

    @Test
    public void metersToMiles3() {
        assertEquals(new BigDecimal("2222.0"), ApiMath.metersToMiles(BigDecimal.valueOf(3575982.4F)));
    }

    @Test
    public void metersToMiles4() {
        assertEquals(new BigDecimal("1525.0"), ApiMath.metersToMiles(BigDecimal.valueOf(2454234.7F)));
    }

    @Test
    public void metersToMiles5() {
        assertEquals(new BigDecimal("1835.0"), ApiMath.metersToMiles(BigDecimal.valueOf(2953127.4F)));
    }

    @Test
    public void metersToMiles6() {
        assertEquals(new BigDecimal("1500.0"), ApiMath.metersToMiles(BigDecimal.valueOf(2414000.8F)));
    }

    @Test
    public void metersToMiles7() {
        assertEquals(new BigDecimal("1206.0"), ApiMath.metersToMiles(BigDecimal.valueOf(1940856.8F)));
    }

}
