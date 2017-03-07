package com.irunninglog.spring.dashboard;

import com.irunninglog.api.Progress;
import com.irunninglog.api.dashboard.IProgressInfo;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.spring.data.IShoeEntityRepository;
import com.irunninglog.spring.data.ShoeEntity;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.service.InternalService;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@InternalService
final class     DashboardShoesService {

    private final IShoeEntityRepository shoeEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final MathService mathService;
    private final DateService dateService;
    private final IFactory factory;

    @Autowired
    public DashboardShoesService(IShoeEntityRepository shoeEntityRepository,
                                 IWorkoutEntityRepository workoutEntityRepository,
                                 MathService mathService,
                                 DateService dateService,
                                 IFactory factory) {
        super();

        this.shoeEntityRepository = shoeEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.mathService = mathService;
        this.dateService = dateService;
        this.factory = factory;
    }

    Collection<IProgressInfo> shoes(ProfileEntity profile) {
        List<IProgressInfo> progressList = new ArrayList<>();

        for (ShoeEntity shoeEntity : shoeEntityRepository.dashboardShoes(profile.getId())) {
            BigDecimal distance = workoutEntityRepository.shoeMileage(profile.getId(), shoeEntity.getId());
            if (distance == null) {
                distance = BigDecimal.ZERO;
            }

            BigDecimal target = new BigDecimal(shoeEntity.getMax());

            Progress progress = mathService.progress(distance, target, Boolean.TRUE);

            IProgressInfo progressInfo = factory.get(IProgressInfo.class)
                    .setTitle(shoeEntity.getName())
                    .setSubTitle(shoeEntity.getDescription())
                    .setTextOne(formatStart(shoeEntity))
                    .setTextTwo(mathService.formatProgressText(distance, target, profile.getPreferredUnits()))
                    .setMax(target.intValue())
                    .setValue(Math.min(target.intValue(), distance.intValue()))
                    .setProgress(progress);

            progressList.add(progressInfo);
        }

        progressList.sort(new ProgressInfoComparator());

        return progressList;
    }

    private String formatStart(ShoeEntity shoeEntity) {
        return shoeEntity.getStartDate() == null ? null : "Started " + dateService.formatMedium(shoeEntity.getStartDate());
    }

}
