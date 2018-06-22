package com.irunninglog.math;

import com.irunninglog.api.progress.Progress;
import com.irunninglog.api.progress.ProgressThresholds;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ApiMathTest {

    private final ApiMath apiMath = new ApiMath();

    @Test
    public void miles() {
        assertEquals(new BigDecimal("3.4999965530615234375"), apiMath.miles(BigDecimal.valueOf(5632.7F)));
        assertEquals(new BigDecimal("530.0229386045"), apiMath.miles(BigDecimal.valueOf(852989.5F)));
        assertEquals(new BigDecimal("2222.0118220075"), apiMath.miles(BigDecimal.valueOf(3575982.4F)));
        assertEquals(new BigDecimal("1524.99030084225"), apiMath.miles(BigDecimal.valueOf(2454234.7F)));
        assertEquals(new BigDecimal("1834.9877878025"), apiMath.miles(BigDecimal.valueOf(2953127.4F)));
        assertEquals(new BigDecimal("1499.99006002825"), apiMath.miles(BigDecimal.valueOf(2414000.8F)));
        assertEquals(new BigDecimal("1205.99209960425"), apiMath.miles(BigDecimal.valueOf(1940856.8F)));
    }

    @Test
    public void round() {
        assertEquals(new BigDecimal("3.5"), apiMath.round(new BigDecimal("3.4999965530615234375")));
        assertEquals(new BigDecimal("530.0"), apiMath.round(new BigDecimal("530.0229386045")));
        assertEquals(new BigDecimal("2222.0"), apiMath.round(new BigDecimal("2222.0118220075")));
        assertEquals(new BigDecimal("1525.0"), apiMath.round(new BigDecimal("1524.99030084225")));
        assertEquals(new BigDecimal("1835.0"), apiMath.round(new BigDecimal("1834.9877878025")));
        assertEquals(new BigDecimal("1500.0"), apiMath.round(new BigDecimal("1499.99006002825")));
        assertEquals(new BigDecimal("1206.0"), apiMath.round(new BigDecimal("1205.99209960425")));
    }

    @Test
    public void floor() {
        assertEquals(new BigDecimal(1), apiMath.floor(BigDecimal.valueOf(1.9999999999)));
        assertEquals(new BigDecimal(1), apiMath.floor(BigDecimal.valueOf(1.0000000001)));
        assertEquals(new BigDecimal(-1), apiMath.floor(BigDecimal.valueOf(-.8)));
    }

    @Test
    public void format() {
        assertEquals("1,000", apiMath.format(BigDecimal.valueOf(1000), ApiMath.FORMAT_FORMATTED));
        assertEquals("1000", apiMath.format(BigDecimal.valueOf(1000), ApiMath.FORMAT_PLAIN));
        assertEquals("1,000 mi", apiMath.format(BigDecimal.valueOf(1000), ApiMath.FORMAT_FORMATTED_MILEAGE));
    }

    @Test
    public void percentage() {
        assertEquals(66, apiMath.percentage(BigDecimal.valueOf(30), BigDecimal.valueOf(20)).intValue());
    }

    @Test
    public void Progress() {
        ProgressThresholds thresholds1 = new ProgressThresholds(20, 60, ProgressThresholds.ProgressMode.LOW_BAD);
        ProgressThresholds thresholds2 = new ProgressThresholds(20, 60, ProgressThresholds.ProgressMode.LOW_GOOD);

        assertEquals(Progress.BAD, apiMath.progress(BigDecimal.valueOf(19.99999), thresholds1));
        assertEquals(Progress.GOOD, apiMath.progress(BigDecimal.valueOf(19.99999), thresholds2));
        assertEquals(Progress.OK, apiMath.progress(BigDecimal.valueOf(20), thresholds1));
        assertEquals(Progress.OK, apiMath.progress(BigDecimal.valueOf(20), thresholds2));
        assertEquals(Progress.GOOD, apiMath.progress(BigDecimal.valueOf(100), thresholds1));
        assertEquals(Progress.BAD, apiMath.progress(BigDecimal.valueOf(100), thresholds2));
    }

}
