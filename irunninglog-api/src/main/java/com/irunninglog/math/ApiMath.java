package com.irunninglog.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApiMath {

    private static final BigDecimal METERS_TO_MILES = BigDecimal.valueOf(0.000621371);

    private ApiMath() { }

    public static BigDecimal metersToMiles(BigDecimal meters) {
        return meters.multiply(METERS_TO_MILES).setScale(1, RoundingMode.HALF_UP);
    }

}
