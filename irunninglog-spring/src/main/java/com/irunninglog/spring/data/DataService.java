package com.irunninglog.spring.data;

import com.irunninglog.api.data.*;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.math.MathService;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ApiService
final class DataService implements IDataService {

    private final IRouteEntityRespository routeEntityRespository;
    private final IRunEntityRepository runEntityRepository;
    private final IShoeEntityRepository shoeEntityRepository;
    private final DateService dateService;
    private final MathService mathService;
    private final IFactory factory;

    @Autowired
    public DataService(IRouteEntityRespository routeEntityRespository,
                       IRunEntityRepository runEntityRepository,
                       IShoeEntityRepository shoeEntityRepository,
                       DateService dateService,
                       MathService mathService,
                       IFactory factory) {

        this.routeEntityRespository = routeEntityRespository;
        this.runEntityRepository = runEntityRepository;
        this.shoeEntityRepository = shoeEntityRepository;
        this.dateService = dateService;
        this.mathService = mathService;
        this.factory = factory;
    }

    @Override
    public IShoes shoes(long profileId) {
        List<ShoeEntity> shoeEntityList = shoeEntityRepository.findByProfileId(profileId);

        IShoes shoes = factory.get(IShoes.class);
        for (ShoeEntity shoeEntity : shoeEntityList) {
            shoes.getShoes().add(factory.get(IShoe.class)
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

        return shoes;
    }

    @Override
    public IRuns runs(long profileId) {
        List<RunEntity> runEntityList = runEntityRepository.findByProfileId(profileId);

        IRuns runs = factory.get(IRuns.class);
        for (RunEntity runEntity : runEntityList) {
            runs.getRuns().add(factory.get(IRun.class)
                    .setId(runEntity.getId())
                    .setName(runEntity.getName())
                    .setDescription(runEntity.getDescription())
                    .setDashboard(runEntity.isDashboard()));
        }

        runs.getRuns().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        return runs;
    }


    @Override
    public IRoutes routes(long profileId) {
        List<RouteEntity> routeEntityList = routeEntityRespository.findByProfileId(profileId);

        IRoutes routes = factory.get(IRoutes.class);
        for (RouteEntity routeEntity : routeEntityList) {
            routes.getRoutes().add(factory.get(IRoute.class)
                    .setId(routeEntity.getId())
                    .setName(routeEntity.getName())
                    .setDescription(routeEntity.getDescription())
                    .setDashboard(routeEntity.isDashboard()));
        }

        routes.getRoutes().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

        return routes;
    }

}
