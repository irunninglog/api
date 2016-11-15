package com.irunninglog.api.dashboard.impl;

import com.irunninglog.api.Progress;
import com.irunninglog.api.Unit;
import com.irunninglog.api.dashboard.*;
import com.irunninglog.api.data.impl.IShoeEntityRepository;
import com.irunninglog.api.data.impl.ShoeEntity;
import com.irunninglog.api.date.IDateService;
import com.irunninglog.api.math.IMathService;
import com.irunninglog.api.profile.impl.IProfileEntityRepository;
import com.irunninglog.api.profile.impl.ProfileEntity;
import com.irunninglog.api.service.ResponseStatus;
import com.irunninglog.api.service.ResponseStatusException;
import com.irunninglog.api.workout.impl.IWorkoutEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class DashboardService implements IDashboardService {

    private static final String NO_PROGRESS = "No progress to track";
    private static final String FORMAT_PROGRESS = "{0} of {1} ({2}%)";

    private final IProfileEntityRepository profileEntityRepository;
    private final IShoeEntityRepository shoeEntityRepository;
    private final IWorkoutEntityRepository workoutEntityRepository;
    private final IMathService mathService;
    private final IDateService dateService;

    @Autowired
    public DashboardService(IProfileEntityRepository profileEntityRepository,
                            IShoeEntityRepository shoeEntityRepository,
                            IWorkoutEntityRepository workoutEntityRepository,
                            IMathService mathService,
                            IDateService dateService) {
        super();

        this.profileEntityRepository = profileEntityRepository;
        this.shoeEntityRepository = shoeEntityRepository;
        this.workoutEntityRepository = workoutEntityRepository;
        this.mathService = mathService;
        this.dateService = dateService;
    }

    @Override
    public DashboardResponse get(DashboardRequest request) {
        ProfileEntity userEntity = profileEntityRepository.findOne(request.getId());
        if (userEntity == null) {
            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        DashboardInfo info = new DashboardInfo();

        info.getProgress().addAll(progress());
        info.getGoals().addAll(goals());
        info.getShoes().addAll(shoes(userEntity));
        info.getStreaks().addAll(streaks());

        return new DashboardResponse()
                .setBody(info)
                .setStatus(ResponseStatus.Ok);
    }

    private Collection<ProgressInfo> progress() {
        return Collections.emptyList();
    }

    private Collection<ProgressInfo> goals() {
        return Collections.emptyList();
    }

    private Collection<ProgressInfo> streaks() {
        return Collections.emptyList();
    }

    private Collection<ProgressInfo> shoes(ProfileEntity profileEntity) {
        List<ProgressInfo> progressList = new ArrayList<>();

        for (ShoeEntity shoeEntity : shoeEntityRepository.dashboardShoes(profileEntity.getId())) {
            BigDecimal distance = workoutEntityRepository.shoeMileage(profileEntity.getId(), shoeEntity.getId());
            if (distance == null) {
                distance = BigDecimal.ZERO;
            }

            BigDecimal target = new BigDecimal(shoeEntity.getMax());

            Progress progress = mathService.progress(distance, target, Boolean.TRUE);

            ProgressInfo progressInfo = new ProgressInfo()
                    .setTitle(shoeEntity.getName())
                    .setSubTitle(shoeEntity.getDescription())
                    .setTextOne(formatStart(shoeEntity))
                    .setTextTwo(formatProgressText(distance, target, profileEntity.getPreferredUnits()))
                    .setMax(target.intValue())
                    .setValue(Math.min(target.intValue(), distance.intValue()))
                    .setProgress(progress);

            progressList.add(progressInfo);
        }

        return progressList;
    }

    private String formatProgressText(BigDecimal mileage, BigDecimal target, Unit units) {
        if (target.doubleValue() < 1E-9) {
            return NO_PROGRESS;
        }

        if (mileage.compareTo(target) > 0) {
            return MessageFormat.format(FORMAT_PROGRESS,
                    mathService.format(mileage, units),
                    mathService.format(target, units),
                    100);
        } else {
            BigDecimal percent = mathService.divide(mileage.multiply(new BigDecimal(100)), target);

            return MessageFormat.format(FORMAT_PROGRESS,
                    mathService.format(mileage, units),
                    mathService.format(target, units),
                    mathService.intValue(percent));
        }
    }

    private String formatStart(ShoeEntity shoeEntity) {
        return shoeEntity.getStartDate() == null ? null : "Started " + dateService.formatMedium(shoeEntity.getStartDate());
    }

}
