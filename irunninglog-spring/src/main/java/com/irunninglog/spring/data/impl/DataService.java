package com.irunninglog.spring.data.impl;

import com.irunninglog.data.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ApiService
public final class DataService implements IDataService {

    private final IRunEntityRepository runEntityRepository;
    private final IShoeEntityRepository shoeEntityRepository;
    private final DateService dateService;
    private final MathService mathService;

    @Autowired
    public DataService(IRunEntityRepository runEntityRepository,
                       IShoeEntityRepository shoeEntityRepository,
                       DateService dateService,
                       MathService mathService) {

        this.runEntityRepository = runEntityRepository;
        this.shoeEntityRepository = shoeEntityRepository;
        this.dateService = dateService;
        this.mathService = mathService;
    }

    @Override
    public GetShoesResponse shoes(GetDataRequest request) {
        List<ShoeEntity> shoeEntityList = shoeEntityRepository.findByProfileId(request.getId());

        Shoes shoes = new Shoes();
        for (ShoeEntity shoeEntity : shoeEntityList) {
            shoes.getShoes().add(new Shoe()
                    .setId(shoeEntity.getId())
                    .setName(shoeEntity.getName())
                    .setDescription(shoeEntity.getDescription())
                    .setDashboard(shoeEntity.isDashboard())
                    .setStartDate(shoeEntity.getStartDate() == null ? "" : dateService.format(shoeEntity.getStartDate()))
                    .setMax(shoeEntity.getMax() < 1E-9 ? "--" : mathService.format(shoeEntity.getMax(), shoeEntity.getProfile().getPreferredUnits())));
        }

        shoes.getShoes().sort((o1, o2) -> {
            if ((o1.getStartDate() == null || o1.getStartDate().isEmpty())
                    && (o2.getStartDate() == null || o2.getStartDate().isEmpty())) {
                return o1.getName().compareTo(o2.getName());
            } else if (o1.getStartDate() == null && o2.getStartDate() != null) {
                return 1;
            } else if (o1.getStartDate() != null && o2.getStartDate() == null) {
                return -1;
            } else {
                return o2.getStartDate().compareTo(o1.getStartDate());
            }
        });

        return new GetShoesResponse().setBody(shoes).setStatus(ResponseStatus.Ok);
    }

    @Override
    public GetRunsResponse runs(GetDataRequest request) {
        List<RunEntity> runEntityList = runEntityRepository.findByProfileId(request.getId());

        Runs runs = new Runs();
        for (RunEntity runEntity : runEntityList) {
            runs.getRuns().add(new Run()
                    .setId(runEntity.getId())
                    .setName(runEntity.getName())
                    .setDescription(runEntity.getDescription())
                    .setDashboard(runEntity.isDashboard()));
        }

        runs.getRuns().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        return new GetRunsResponse().setBody(runs).setStatus(ResponseStatus.Ok);
    }

}
