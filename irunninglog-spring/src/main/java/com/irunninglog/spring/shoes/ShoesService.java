package com.irunninglog.spring.shoes;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.spring.util.DistanceService;
import com.irunninglog.strava.IStravaService;
import com.irunninglog.strava.IStravaShoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
final class ShoesService implements IShoesService {

    private final IStravaService stravaService;
    private final IFactory factory;
    private final DistanceService distanceService;

    @Autowired
    ShoesService(IStravaService stravaService,
                 IFactory factory,
                 DistanceService distanceService) {

        super();

        this.stravaService = stravaService;
        this.factory = factory;
        this.distanceService = distanceService;
    }

    @Override
    public List<IShoe> getShoes(IUser user) {
        List<IStravaShoe> stravaShoes = stravaService.shoes(user);

        stravaShoes.sort((o1, o2) ->  Float.compare(o2.getDistance(), o1.getDistance()));

        return stravaShoes.stream().map(stravaShoe -> factory.get(IShoe.class)
                .setId(stravaShoe.getId())
                .setName(stravaShoe.getName())
                .setBrand(stravaShoe.getBrand())
                .setModel(stravaShoe.getModel())
                .setDescription(stravaShoe.getDescription())
                .setPrimary(stravaShoe.isPrimary())
                .setPercentage(distanceService.percentage(804672.0F, stravaShoe.getDistance()))
                .setProgress(distanceService.progressWhereLowIsGood(distanceService.percentage(804672.0F, stravaShoe.getDistance())))
                .setDistance(distanceService.mileage(stravaShoe.getDistance()))).collect(Collectors.toList());
    }

}
