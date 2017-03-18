package com.irunninglog.spring.math;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;
import com.irunninglog.spring.service.InternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

@InternalService
public final class MathService {

    private static final Logger LOG = LoggerFactory.getLogger(MathService.class);

    private static final String NO_PROGRESS = "No progress to track";
    private static final String FORMAT_PROGRESS = "{0} of {1} ({2}%)";
    private static final BigDecimal CONVERTER = BigDecimal.valueOf(1.609344);

    private BigDecimal round(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    public Progress progress(BigDecimal number, BigDecimal target) {
        return progress(number, target, Boolean.FALSE);
    }

    public Progress progress(final BigDecimal input, final BigDecimal target, final boolean inverted) {
        Progress progress;

        BigDecimal number = round(input);
        BigDecimal target1 = round(target.multiply(BigDecimal.valueOf(.2)));
        BigDecimal target2 = round(target.multiply(BigDecimal.valueOf(.8)));

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
            BigDecimal percent = divide(mileage.multiply(BigDecimal.valueOf(100)), target);

            return MessageFormat.format(FORMAT_PROGRESS,
                    format(mileage, units),
                    format(target, units),
                    intValue(percent));
        }
    }

    public String format(double number, Unit units) {
        return format(BigDecimal.valueOf(number), units);
    }

    public String format(final BigDecimal input, final Unit units) {
        BigDecimal number = units == Unit.Metric ? input.multiply(CONVERTER) : input;

        return DecimalFormat.getInstance().format(number.setScale(2, RoundingMode.HALF_UP)) + (units == Unit.English ? " mi" : " km");
    }

    public int intValue(BigDecimal number) {
        return number.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public BigDecimal divide(BigDecimal top, BigDecimal bottom) {
        return top.divide(bottom, 2, RoundingMode.HALF_UP);
    }

    public int getPercentage(int value, int max) {
        return getPercentage(BigDecimal.valueOf(value), BigDecimal.valueOf(max));
    }

    private int getPercentage(BigDecimal value, BigDecimal max) {
        return max.doubleValue() < 1E-9 ? 0 : intValue(divide(value.multiply(BigDecimal.valueOf(100)), max));
    }

    public String formatShort(double total, Unit units) {
        BigDecimal number = BigDecimal.valueOf(total);

        if (units == Unit.Metric) {
            number = number.multiply(CONVERTER);
        }

        return number.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public double parse(String value) {
        try {
            return NumberFormat.getInstance(Locale.getDefault()).parse(value).doubleValue();
        } catch (Exception ex) {
            String errMsg = "Unable to parse " + value;
            LOG.error(errMsg, ex);
            throw new IllegalArgumentException(errMsg, ex);
        }
    }

}
