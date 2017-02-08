package com.irunninglog.spring.report.impl;

import com.irunninglog.api.report.*;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.spring.data.impl.*;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
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
    public IGetMultiSetResponse mileageByMonth(IGetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        IMultiSet multiSet = mileageByMonthService.multiSet(workoutEntityRepository.findByProfileId(profileEntity.getId()), profileEntity);

        return new IGetMultiSetResponse().setBody(multiSet).setStatus(ResponseStatus.Ok);
    }

    @Override
    public IGetDataSetResponse mileageByRoute(IGetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        IDataSet dataSet = mileageByDataService.dataSet(routeEntityRespository.findByProfileId(profileEntity.getId()),
                this::routeTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());

        return new IGetDataSetResponse().setBody(dataSet).setStatus(ResponseStatus.Ok);
    }

    private BigDecimal routeTotal(IdParameters parameters) {
        return workoutEntityRepository.routeMileage(parameters.profileId, parameters.dataId);
    }

    @Override
    public IGetDataSetResponse mileageByRun(IGetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        IDataSet dataSet = mileageByDataService.dataSet(runEntityRepository.findByProfileId(profileEntity.getId()),
                this::runTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());

        return new IGetDataSetResponse().setBody(dataSet).setStatus(ResponseStatus.Ok);
    }

    private BigDecimal runTotal(IdParameters parameters) {
        return workoutEntityRepository.runMileage(parameters.profileId, parameters.dataId);
    }

    @Override
    public IGetDataSetResponse mileageByShoe(IGetReportRequest request) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(request.getId());

        IDataSet dataSet = mileageByDataService.dataSet(shoeEntityRepository.findByProfileId(profileEntity.getId()),
                this::shoeTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());

        return new IGetDataSetResponse().setBody(dataSet).setStatus(ResponseStatus.Ok);
    }

    private BigDecimal shoeTotal(IdParameters parameters) {
        return workoutEntityRepository.shoeMileage(parameters.profileId, parameters.dataId);
    }

}
