package com.irunninglog.spring.challenges;

import com.irunninglog.api.Progress;
import com.irunninglog.api.challenges.IChallenge;
import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class ChallengesService implements IChallengesService {

    private final ChallengeDefinitions definitions;
    private final IFactory factory;
    private final IStravaService stravaService;

    @Autowired
    ChallengesService(ChallengeDefinitions definitions,
                      IFactory factory,
                      IStravaService stravaService) {
        super();

        this.definitions = definitions;
        this.factory = factory;
        this.stravaService = stravaService;
    }

    @Override
    public List<IChallenge> getChallenges(IUser user) {
        List<IStravaRun> runs = stravaService.runs(user);
        float total = 0.0F;
        for (IStravaRun run : runs) {
            total += run.getDistance();
        }

        final float done = total;
        return definitions.definitions().stream().map(definition -> factory.get(IChallenge.class)
                .setName(definition.getName())
                .setDescription(definition.getDesctiption())
                .setDistanceTotal(mileage(definition.getDistance()))
                .setDistanceDone(mileage(Math.min(definition.getDistance(), done)))
                .setPercentage(percentage(definition.getDistance(), done))
                .setProgress(progress(percentage(definition.getDistance(), done))))
                .collect(Collectors.toList());
    }

    private Progress progress(int percentage) {
        if (percentage < 40) {
            return Progress.BAD;
        } else if (percentage < 80) {
            return Progress.OK;
        } else {
            return Progress.GOOD;
        }
    }

    private int percentage(float total, float done) {
        double result = done * 100.0 / total;
        return Math.min(100, BigDecimal.valueOf(result).setScale(1, BigDecimal.ROUND_HALF_UP).intValue());
    }

    private String mileage(float distance) {
        return DecimalFormat.getInstance()
                .format(BigDecimal.valueOf(distance)
                        .multiply(BigDecimal.valueOf(0.000621371)).setScale(1, RoundingMode.HALF_UP)) + " mi";
    }

}
