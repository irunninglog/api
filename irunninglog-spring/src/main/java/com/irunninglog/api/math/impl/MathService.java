package com.irunninglog.api.math.impl;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;
import com.irunninglog.api.math.IMathService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class MathService implements IMathService {

    private static final BigDecimal CONVERTER = new BigDecimal(1.609344);

    @Override
    public BigDecimal round(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
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

    @Override
    public String format(BigDecimal number, Unit units) {
        if (units == Unit.Metric) {
            number = number.multiply(CONVERTER);
        }
        return DecimalFormat.getInstance().format(number.setScale(2, RoundingMode.HALF_UP)) + (units == Unit.English ? " mi" : " km");
    }

    @Override
    public int intValue(BigDecimal number) {
        return number.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    @Override
    public BigDecimal divide(BigDecimal top, BigDecimal bottom) {
        return top.divide(bottom, 2, RoundingMode.HALF_UP);
    }

}
