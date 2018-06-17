package com.irunninglog.spring.shoes;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.progress.ProgressThresholds;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.math.ApiMath;
import com.irunninglog.strava.IStravaService;
import com.irunninglog.strava.IStravaShoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class ShoesService implements IShoesService {

    private final IStravaService stravaService;
    private final IFactory factory;

    @Autowired
    ShoesService(IStravaService stravaService,
                 IFactory factory) {

        super();

        this.stravaService = stravaService;
        this.factory = factory;
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
                .setPercentage(ApiMath.percentage(BigDecimal.valueOf(804672.0F), BigDecimal.valueOf(stravaShoe.getDistance())))
                .setProgress(ApiMath.progress(BigDecimal.valueOf(ApiMath.percentage(BigDecimal.valueOf(804672.0F), BigDecimal.valueOf(stravaShoe.getDistance()))), new ProgressThresholds(40, 80, ProgressThresholds.ProgressMode.LOW_GOOD)))
                .setDistance(ApiMath.format(ApiMath.round(ApiMath.miles(BigDecimal.valueOf(stravaShoe.getDistance()))), ApiMath.FORMAT_FORMATTED_MILEAGE))).collect(Collectors.toList());
    }

}
