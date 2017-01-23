package com.irunninglog.spring.data.impl;

import com.irunninglog.data.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ApiService
public class DataService implements IDataService {

    private final IShoeEntityRepository shoeEntityRepository;
    private final DateService dateService;
    private final MathService mathService;

    @Autowired
    public DataService(IShoeEntityRepository shoeEntityRepository,
                       DateService dateService,
                       MathService mathService) {

        this.shoeEntityRepository = shoeEntityRepository;
        this.dateService = dateService;
        this.mathService = mathService;
    }

    @Override
    public GetShoesResponse shoes(GetDataRequest request) {
        List<ShoeEntity> shoeEntityList = shoeEntityRepository.shoes(request.getId());

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

}
