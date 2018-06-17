package com.irunninglog.spring.challenges;

import com.irunninglog.api.challenges.IChallenge;
import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.progress.ProgressThresholds;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.math.ApiMath;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        List<IRun> runs = stravaService.runs(user);
        BigDecimal total = BigDecimal.ZERO;
        for (IRun run : runs) {
            total = total.add(new BigDecimal(run.getDistance()));
        }

        final BigDecimal done = total;
        return definitions.definitions().stream().map(definition -> factory.get(IChallenge.class)
                .setName(definition.getName())
                .setDescription(definition.getDesctiption())
                .setDistanceTotal(ApiMath.format(ApiMath.round(ApiMath.miles(definition.getDistance())), ApiMath.FORMAT_FORMATTED_MILEAGE))
                .setDistanceDone(ApiMath.format(ApiMath.round(ApiMath.miles(BigDecimal.valueOf(Math.min(definition.getDistance().floatValue(), done.floatValue())))), ApiMath.FORMAT_FORMATTED_MILEAGE))
                .setDistanceInt(ApiMath.floor(ApiMath.round(ApiMath.miles(definition.getDistance()))).intValue())
                .setPercentage(ApiMath.percentage(definition.getDistance(), done))
                .setProgress(ApiMath.progress(BigDecimal.valueOf(ApiMath.percentage(definition.getDistance(), done)), new ProgressThresholds(20, 80, ProgressThresholds.ProgressMode.LOW_BAD))))
                .collect(Collectors.toList());
    }

}
