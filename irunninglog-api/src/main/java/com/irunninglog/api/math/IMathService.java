package com.irunninglog.api.math;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;

import java.math.BigDecimal;

public interface IMathService {

    BigDecimal round(BigDecimal bigDecimal);

    Progress progress(BigDecimal number, BigDecimal target);

    Progress progress(BigDecimal number, BigDecimal target, boolean inverted);

    String formatProgressText(BigDecimal mileage, BigDecimal target, Unit units);

    String format(double number, Unit units);

    String format(BigDecimal number, Unit units);

    int intValue(BigDecimal number);

    BigDecimal divide(BigDecimal top, BigDecimal bottom);

}
