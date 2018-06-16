package com.irunninglog.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class ApiMath {

    public enum Style {
        Plain, Formatted
    }

    private static final BigDecimal METERS_TO_MILES = BigDecimal.valueOf(0.000621371);

    private ApiMath() { }

    public static BigDecimal miles(BigDecimal meters) {
        return meters.multiply(METERS_TO_MILES);
    }

    public static BigDecimal round(BigDecimal number) {
        return number.setScale(1, RoundingMode.HALF_UP);
    }

    public static BigDecimal floor(BigDecimal number) {
        return number.setScale(0, RoundingMode.FLOOR);
    }

    public static String format(BigDecimal number, Style style) {
        return style == Style.Plain ? DecimalFormat.getInstance().format(number).replace(",", "") : DecimalFormat.getInstance().format(number);
    }

}
