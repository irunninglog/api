package com.irunninglog.spring.report.impl;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;
import com.irunninglog.api.report.IDataPoint;
import com.irunninglog.api.report.IDataSet;
import com.irunninglog.spring.data.AbstractDataEntity;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.service.InternalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@InternalService
class MileageByDataService {

    private final MathService mathService;

    @Autowired
    MileageByDataService(MathService mathService) {
        this.mathService = mathService;
    }

    IDataSet dataSet(List<? extends AbstractDataEntity> list, Function<IdParameters, BigDecimal> total, long profileId, Unit unit) {
        List<IDataPoint> points = new ArrayList<>();
        for (AbstractDataEntity entity : list) {
            BigDecimal distance = total.apply(new IdParameters(entity.getId(), profileId));
            if (distance != null && distance.compareTo(BigDecimal.ZERO) > 0) {
                IDataPoint dataPoint = new IDataPoint()
                        .setLabel(entity.getName())
                        .setValue(mathService.formatShort(distance.doubleValue(), unit))
                        .setProgress(Progress.None);

                points.add(dataPoint);
            }
        }

        points.sort((o1, o2) -> o1.getLabel().compareTo(o2.getLabel()));

        IDataSet dataSet = new IDataSet()
                .setKey(String.valueOf(profileId))
                .setUnits(unit);

        points.forEach(dataSet::add);

        return dataSet;
    }

}
