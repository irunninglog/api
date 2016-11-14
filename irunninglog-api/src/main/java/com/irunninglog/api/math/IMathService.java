package com.irunninglog.api.math;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;

import java.math.BigDecimal;

public interface IMathService {

    BigDecimal round(BigDecimal bigDecimal);

    Progress progress(BigDecimal number, BigDecimal target, boolean inverted);

    String format(BigDecimal number, Unit units);

    int intValue(BigDecimal number);

    BigDecimal divide(BigDecimal top, BigDecimal bottom);

}
