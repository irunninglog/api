package com.irunninglog.math;

import com.irunninglog.api.progress.Progress;
import com.irunninglog.api.progress.ProgressThresholds;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

public final class ApiMath {

    public static final String FORMAT_PLAIN = "{0,number,#}";
    public static final String FORMAT_FORMATTED = "{0,number}";
    public static final String FORMAT_FORMATTED_MILEAGE = "{0,number} mi";

    private static final BigDecimal METERS_TO_MILES = BigDecimal.valueOf(0.000621371);

    public BigDecimal miles(BigDecimal meters) {
        return meters.multiply(METERS_TO_MILES);
    }

    public BigDecimal round(BigDecimal number) {
        return number.setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal floor(BigDecimal number) {
        return number.setScale(0, RoundingMode.FLOOR);
    }

    public String format(BigDecimal number, String format) {
        return MessageFormat.format(format, number);
    }

    public BigDecimal percentage(BigDecimal total, BigDecimal done) {
        BigDecimal result = done.multiply(BigDecimal.valueOf(100)).divide(total, 1, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(Math.min(100, result.intValue()));
    }

    public Progress progress(BigDecimal value, ProgressThresholds thresholds) {
        if (value.intValue() < thresholds.getThreshold1()) {
            return thresholds.getMode() == ProgressThresholds.ProgressMode.LOW_GOOD ? Progress.GOOD : Progress.BAD;
        } else if (value.intValue() < thresholds.getThreshold2()) {
            return Progress.OK;
        } else {
            return thresholds.getMode() == ProgressThresholds.ProgressMode.LOW_GOOD ? Progress.BAD : Progress.GOOD;
        }
    }

}
