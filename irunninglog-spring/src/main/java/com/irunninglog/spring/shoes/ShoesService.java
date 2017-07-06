package com.irunninglog.spring.shoes;

import com.irunninglog.api.Progress;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.strava.IStravaService;
import com.irunninglog.strava.IStravaShoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class ShoesService implements IShoesService {

    private final IStravaService stravaService;
    private final IFactory factory;

    @Autowired
    ShoesService(IStravaService stravaService, IFactory factory) {
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
                .setPercentage(percentage(stravaShoe))
                .setProgress(progress(stravaShoe))
                .setDistance(distance(stravaShoe))).collect(Collectors.toList());
    }

    private Progress progress(IStravaShoe stravaShoe) {
        int percentage = percentage(stravaShoe);
        if (percentage < 40) {
            return Progress.GOOD;
        } else if (percentage < 80) {
            return Progress.OK;
        } else {
            return Progress.BAD;
        }
    }

    private int percentage(IStravaShoe stravaShoe) {
        double result = stravaShoe.getDistance() * 100.0 / 804672.0;
        return BigDecimal.valueOf(result).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
    }

    private String distance(IStravaShoe stravaShoe) {
        return DecimalFormat.getInstance().format(BigDecimal.valueOf(stravaShoe.getDistance())
                .multiply(BigDecimal.valueOf(0.000621371))
                .setScale(1, BigDecimal.ROUND_HALF_UP)) + " mi";
    }

}
