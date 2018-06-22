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
    private final ApiMath apiMath;

    @Autowired
    ShoesService(IStravaService stravaService,
                 IFactory factory, ApiMath apiMath) {

        super();

        this.stravaService = stravaService;
        this.factory = factory;
        this.apiMath = apiMath;
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
                .setPercentage(apiMath.percentage(BigDecimal.valueOf(804672.0F), BigDecimal.valueOf(stravaShoe.getDistance())).intValue())
                .setProgress(apiMath.progress(apiMath.percentage(BigDecimal.valueOf(804672.0F), BigDecimal.valueOf(stravaShoe.getDistance())), new ProgressThresholds(40, 80, ProgressThresholds.ProgressMode.LOW_GOOD)))
                .setDistance(apiMath.format(apiMath.round(apiMath.miles(BigDecimal.valueOf(stravaShoe.getDistance()))), ApiMath.FORMAT_FORMATTED_MILEAGE))).collect(Collectors.toList());
    }

}
