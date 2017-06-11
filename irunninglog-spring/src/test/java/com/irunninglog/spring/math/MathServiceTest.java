package com.irunninglog.spring.math;

import com.irunninglog.api.Progress;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MathServiceTest extends AbstractTest {

    private MathService mathService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        mathService = applicationContext.getBean(MathService.class);
    }

    @Test
    public void testFormatBigDecimal1() {
        BigDecimal bigDecimal = new BigDecimal(101.4999);
        assertEquals("101.5 mi", mathService.format(bigDecimal, false));
    }

    @Test
    public void testFormatBigDecimal2() {
        BigDecimal bigDecimal = new BigDecimal(1000);
        assertEquals("1,000 mi", mathService.format(bigDecimal, false));
    }

    @Test
    public void testFormatBigDecimal3() {
        BigDecimal bigDecimal = new BigDecimal(66.6666);
        assertEquals("66.67 mi", mathService.format(bigDecimal, false));
    }

    @Test
    public void testFormatDouble1() {
        assertEquals("101.5 mi", mathService.format(101.49999, false));
    }

    @Test
    public void testFormatDouble2() {
        assertEquals("1,000 mi", mathService.format(1000, false));
    }

    @Test
    public void testFormatDouble3() {
        assertEquals("66.67 mi", mathService.format(new BigDecimal(66.6667), false));
    }

    @Test
    public void testFormatAndConvert1() {
        assertEquals("1.61 km", mathService.format(new BigDecimal(1), true));
    }

    @Test
    public void testFormatAndConvert2() {
        assertEquals("16.09 km", mathService.format(new BigDecimal(10), true));
    }

    @Test
    public void testFormatAndConvert3() {
        assertEquals("160.93 km", mathService.format(new BigDecimal(100), true));
    }

    @Test
    public void testFormatAndConvert4() {
        assertEquals("10 km", mathService.format(new BigDecimal(6.213), true));
    }

    @Test
    public void testIntValue1() {
        assertEquals(101, mathService.intValue(new BigDecimal(101.4999)));
    }

    @Test
    public void testIntValue2() {
        assertEquals(1001, mathService.intValue(new BigDecimal(1000.99)));
    }

    @Test
    public void testIntValue3() {
        assertEquals(101, mathService.intValue(new BigDecimal(100.5)));
    }

    @Test
    public void testDivide1() {
        double result = mathService.divide(new BigDecimal(6236.232), new BigDecimal(5345.34344)).doubleValue();
        assertEquals(1.17, result, 1E-9);
    }

    @Test
    public void testDivide2() {
        double result = mathService.divide(new BigDecimal(1004.999999), new BigDecimal(10)).doubleValue();
        assertEquals(100.5, result, 1E-9);
    }

    @Test
    public void testDivideByZero() {
        try {
            mathService.divide(new BigDecimal(100), new BigDecimal(0));
            fail("Should not have been able to divide by zero");
        } catch (ArithmeticException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void progress() {
        assertEquals(Progress.NONE, mathService.progress(new BigDecimal(0), new BigDecimal(0)));
        assertEquals(Progress.BAD, mathService.progress(new BigDecimal(10), new BigDecimal(100)));
        assertEquals(Progress.OK, mathService.progress(new BigDecimal(30), new BigDecimal(100)));
        assertEquals(Progress.GOOD, mathService.progress(new BigDecimal(90), new BigDecimal(100)));
    }

    @Test
    public void formatProgressTest() {
        assertEquals("No progress to track", mathService.formatProgressText(new BigDecimal(0), new BigDecimal(0), false));
        assertEquals("50 mi of 100 mi (50%)", mathService.formatProgressText(new BigDecimal(50), new BigDecimal(100), false));
        assertEquals("101 mi of 100 mi (100%)", mathService.formatProgressText(new BigDecimal(101), new BigDecimal(100), false));
    }

    @Test
    public void getPercentage() {
        assertEquals(50, mathService.getPercentage(25, 50));
    }

    @Test
    public void formatShort() {
        assertEquals("100.00", mathService.formatShort(100, false));
        assertEquals("160.93", mathService.formatShort(100, true));
    }

    @Test
    public void parseFail() {
        try {
            mathService.parse("foo");

            fail("Should have thrown");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Unable to"));
        }
    }

}
