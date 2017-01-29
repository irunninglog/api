package com.irunninglog.spring.report.impl;

import com.irunninglog.Progress;
import com.irunninglog.report.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.data.impl.*;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.spring.workout.impl.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiService
public final class ReportService implements IReportService {

    private final IProfileEntityRepository profileEntityRepository;
    private final IRouteEntityRespository routeEntityRespository;
    private final IRunEntityRepository runEntityRepository;
    private final IShoeEntityRepository shoeEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final MathService mathService;

    @Autowired
    public ReportService(IProfileEntityRepository profileEntityRepository,
                         IRouteEntityRespository routeEntityRespository,
                         IRunEntityRepository runEntityRepository,
                         IShoeEntityRepository shoeEntityRepository,
                         IWorkoutEntityRepository workoutEntityRepository,
                         MathService mathService) {

        this.profileEntityRepository = profileEntityRepository;
        this.routeEntityRespository = routeEntityRespository;
        this.runEntityRepository = runEntityRepository;
        this.shoeEntityRepository = shoeEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.mathService = mathService;
    }

    @Override
    public GetMultiSetResponse mileageByMonth(GetReportRequest request) {
        return new GetMultiSetResponse().setBody(new MultiSet()).setStatus(ResponseStatus.Ok);
    }

    @Override
    public GetDataSetResponse mileageByRoute(GetReportRequest request) {
        return new GetDataSetResponse().setBody(routesDataSet(request.getId())).setStatus(ResponseStatus.Ok);
    }

    private DataSet routesDataSet(long id) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(id);

        List<RouteEntity> routes = routeEntityRespository.findByProfileId(profileEntity.getId());
        List<DataPoint> points = new ArrayList<>();
        for (RouteEntity route : routes) {
            BigDecimal distance = workoutEntityRepository.routeMileage(route.getId());
            if (distance != null && distance.compareTo(BigDecimal.ZERO) > 0) {
                DataPoint dataPoint = new DataPoint()
                        .setLabel(route.getName())
                        .setValue(mathService.formatShort(distance.doubleValue(), profileEntity.getPreferredUnits()))
                        .setProgress(Progress.None);

                points.add(dataPoint);
            }
        }

        points.sort((o1, o2) -> o1.getLabel().compareTo(o2.getLabel()));

        DataSet dataSet = new DataSet();
        points.forEach(dataSet::add);

        return dataSet;
    }

    @Override
    public GetDataSetResponse mileageByRun(GetReportRequest request) {
        return new GetDataSetResponse().setBody(runsDataSet(request.getId())).setStatus(ResponseStatus.Ok);
    }

    private DataSet runsDataSet(long id) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(id);

        List<RunEntity> runs = runEntityRepository.findByProfileId(profileEntity.getId());
        List<DataPoint> points = new ArrayList<>();
        for (RunEntity run : runs) {
            BigDecimal distance = workoutEntityRepository.runMileage(run.getId());
            if (distance != null && distance.compareTo(BigDecimal.ZERO) > 0) {
                DataPoint dataPoint = new DataPoint()
                        .setLabel(run.getName())
                        .setValue(mathService.formatShort(distance.doubleValue(), profileEntity.getPreferredUnits()))
                        .setProgress(Progress.None);

                points.add(dataPoint);
            }
        }

        points.sort((o1, o2) -> o1.getLabel().compareTo(o2.getLabel()));

        DataSet dataSet = new DataSet();
        points.forEach(dataSet::add);

        return dataSet;
    }

    @Override
    public GetDataSetResponse mileageByShoe(GetReportRequest request) {
        return new GetDataSetResponse().setBody(shoesDataSet(request.getId())).setStatus(ResponseStatus.Ok);
    }

    private DataSet shoesDataSet(long id) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(id);

        List<ShoeEntity> shoes = shoeEntityRepository.findByProfileId(profileEntity.getId());
        List<DataPoint> points = new ArrayList<>();
        for (ShoeEntity shoe : shoes) {
            BigDecimal distance = workoutEntityRepository.shoeMileage(profileEntity.getId(), shoe.getId());
            if (distance != null && distance.compareTo(BigDecimal.ZERO) > 0) {
                DataPoint dataPoint = new DataPoint()
                        .setLabel(shoe.getName())
                        .setValue(mathService.formatShort(distance.doubleValue(), profileEntity.getPreferredUnits()))
                        .setProgress(Progress.None);

                points.add(dataPoint);
            }
        }

        points.sort((o1, o2) -> o1.getLabel().compareTo(o2.getLabel()));

        DataSet dataSet = new DataSet();
        points.forEach(dataSet::add);

        return dataSet;
    }

}
