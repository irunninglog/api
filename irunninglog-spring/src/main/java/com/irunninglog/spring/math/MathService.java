package com.irunninglog.spring.math;

import com.irunninglog.Progress;
import com.irunninglog.Unit;
import com.irunninglog.spring.service.InternalService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;

@InternalService
public final class MathService {

    private static final String NO_PROGRESS = "No progress to track";
    private static final String FORMAT_PROGRESS = "{0} of {1} ({2}%)";
    private static final BigDecimal CONVERTER = new BigDecimal(1.609344);

    private BigDecimal round(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    public Progress progress(BigDecimal number, BigDecimal target) {
        return progress(number, target, Boolean.FALSE);
    }

    public Progress progress(BigDecimal number, BigDecimal target, boolean inverted) {
        Progress progress;

        number = round(number);
        BigDecimal target1 = round(target.multiply(new BigDecimal(.2)));
        BigDecimal target2 = round(target.multiply(new BigDecimal(.8)));

        if (target.doubleValue() < 1E-9) {
            progress = Progress.None;
        } else if (number.compareTo(target1) < 0) {
            progress = inverted ? Progress.Good : Progress.Bad;
        } else if (number.compareTo(target2) < 0) {
            progress = Progress.Ok;
        } else {
            progress = inverted ? Progress.Bad : Progress.Good;
        }

        return progress;
    }

    public String formatProgressText(BigDecimal mileage, BigDecimal target, Unit units) {
        if (target.doubleValue() < 1E-9) {
            return NO_PROGRESS;
        }

        if (mileage.compareTo(target) > 0) {
            return MessageFormat.format(FORMAT_PROGRESS,
                    format(mileage, units),
                    format(target, units),
                    100);
        } else {
            BigDecimal percent = divide(mileage.multiply(new BigDecimal(100)), target);

            return MessageFormat.format(FORMAT_PROGRESS,
                    format(mileage, units),
                    format(target, units),
                    intValue(percent));
        }
    }

    public String format(BigDecimal number, Unit units) {
        if (units == Unit.Metric) {
            number = number.multiply(CONVERTER);
        }
        return DecimalFormat.getInstance().format(number.setScale(2, RoundingMode.HALF_UP)) + (units == Unit.English ? " mi" : " km");
    }

    public int intValue(BigDecimal number) {
        return number.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public BigDecimal divide(BigDecimal top, BigDecimal bottom) {
        return top.divide(bottom, 2, RoundingMode.HALF_UP);
    }

    public int getPercentage(int value, int max) {
        return getPercentage(new BigDecimal(value), new BigDecimal(max));
    }

    private int getPercentage(BigDecimal value, BigDecimal max) {
        return max.doubleValue() < 1E-9 ? 0 : intValue(divide(value.multiply(new BigDecimal(100)), max));
    }

}
