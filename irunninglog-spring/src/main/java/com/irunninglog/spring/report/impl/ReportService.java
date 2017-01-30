package com.irunninglog.spring.report.impl;

import com.irunninglog.report.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.data.impl.*;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.spring.workout.impl.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@ApiService
public final class ReportService implements IReportService {

    private final IProfileEntityRepository profileEntityRepository;
    private final IRouteEntityRespository routeEntityRespository;
    private final IRunEntityRepository runEntityRepository;
    private final IShoeEntityRepository shoeEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final MileageByDataService mileageByDataService;
    private final MileageByMonthService mileageByMonthService;

    @Autowired
    public ReportService(IProfileEntityRepository profileEntityRepository,
                         IRouteEntityRespository routeEntityRespository,
                         IRunEntityRepository runEntityRepository,
                         IShoeEntityRepository shoeEntityRepository,
                         IWorkoutEntityRepository workoutEntityRepository,
                         MileageByDataService mileageByDataService,
                         MileageByMonthService mileageByMonthService) {

        this.profileEntityRepository = profileEntityRepository;
        this.routeEntityRespository = routeEntityRespository;
        this.runEntityRepository = runEntityRepository;
        this.shoeEntityRepository = shoeEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.mileageByDataService = mileageByDataService;
        this.mileageByMonthService = mileageByMonthService;
    }

    @Override
    public GetMultiSetResponse mileageByMonth(GetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        MultiSet multiSet = mileageByMonthService.multiSet(workoutEntityRepository.findByProfileId(profileEntity.getId()), profileEntity);

        return new GetMultiSetResponse().setBody(multiSet).setStatus(ResponseStatus.Ok);
    }

    @Override
    public GetDataSetResponse mileageByRoute(GetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        DataSet dataSet = mileageByDataService.dataSet(routeEntityRespository.findByProfileId(profileEntity.getId()),
                this::routeTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());

        return new GetDataSetResponse().setBody(dataSet).setStatus(ResponseStatus.Ok);
    }

    private BigDecimal routeTotal(IdParameters parameters) {
        return workoutEntityRepository.routeMileage(parameters.profileId, parameters.dataId);
    }

    @Override
    public GetDataSetResponse mileageByRun(GetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        DataSet dataSet = mileageByDataService.dataSet(runEntityRepository.findByProfileId(profileEntity.getId()),
                this::runTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());

        return new GetDataSetResponse().setBody(dataSet).setStatus(ResponseStatus.Ok);
    }

    private BigDecimal runTotal(IdParameters parameters) {
        return workoutEntityRepository.runMileage(parameters.profileId, parameters.dataId);
    }

    @Override
    public GetDataSetResponse mileageByShoe(GetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        DataSet dataSet = mileageByDataService.dataSet(shoeEntityRepository.findByProfileId(profileEntity.getId()),
                this::shoeTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());

        return new GetDataSetResponse().setBody(dataSet).setStatus(ResponseStatus.Ok);
    }

    private BigDecimal shoeTotal(IdParameters parameters) {
        return workoutEntityRepository.shoeMileage(parameters.profileId, parameters.dataId);
    }

}
