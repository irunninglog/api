package com.irunninglog.spring.report;

import com.irunninglog.api.report.*;
import com.irunninglog.spring.data.IRouteEntityRespository;
import com.irunninglog.spring.data.IRunEntityRepository;
import com.irunninglog.spring.data.IShoeEntityRepository;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.ApiService;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@ApiService
final class ReportService implements IReportService {

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
    public IMultiSet mileageByMonth(long profileId) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(profileId);

        return mileageByMonthService.multiSet(workoutEntityRepository.findByProfileId(profileEntity.getId()), profileEntity);
    }

    @Override
    public IDataSet mileageByRoute(long profileId) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(profileId);

        return mileageByDataService.dataSet(routeEntityRespository.findByProfileId(profileEntity.getId()),
                this::routeTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());
    }

    private BigDecimal routeTotal(IdParameters parameters) {
        return workoutEntityRepository.routeMileage(parameters.profileId, parameters.dataId);
    }

    @Override
    public IDataSet mileageByRun(long profileId) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(profileId);

        return mileageByDataService.dataSet(runEntityRepository.findByProfileId(profileEntity.getId()),
                this::runTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());
    }

    private BigDecimal runTotal(IdParameters parameters) {
        return workoutEntityRepository.runMileage(parameters.profileId, parameters.dataId);
    }

    @Override
    public IDataSet mileageByShoe(long profileId) {
        ProfileEntity profileEntity = profileEntityRepository.findOne(profileId);

        return mileageByDataService.dataSet(shoeEntityRepository.findByProfileId(profileEntity.getId()),
                this::shoeTotal,
                profileEntity.getId(),
                profileEntity.getPreferredUnits());
    }

    private BigDecimal shoeTotal(IdParameters parameters) {
        return workoutEntityRepository.shoeMileage(parameters.profileId, parameters.dataId);
    }

}
