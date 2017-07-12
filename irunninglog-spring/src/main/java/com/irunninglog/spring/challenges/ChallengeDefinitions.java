package com.irunninglog.spring.challenges;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
final class ChallengeDefinitions {

    private static final String DRIVING_DISTANCE = "Driving distance";

    List<ChallengeDefinition> definitions() {
        List<ChallengeDefinition> definitions = new ArrayList<>();

        definitions.add(new ChallengeDefinition()
                .setName("New York to Boston")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(346160));

        definitions.add(new ChallengeDefinition()
                .setName("London to Rome")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(1880196.6F));

        definitions.add(new ChallengeDefinition()
                .setName("Appalachain Trail")
                .setDesctiption("Official website distance")
                .setDistance(3524463));

        definitions.add(new ChallengeDefinition()
                .setName("New York to Los Angeles")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(4468830));

        definitions.add(new ChallengeDefinition()
                .setName("Around the World")
                .setDesctiption("Distance at the equator")
                .setDistance(40030000));

        definitions.add(new ChallengeDefinition()
                .setName("From the Earth to the Moon")
                .setDesctiption("Average distance")
                .setDistance(384400000));

        return definitions;
    }

}
