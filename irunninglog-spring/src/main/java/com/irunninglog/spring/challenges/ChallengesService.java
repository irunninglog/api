package com.irunninglog.spring.challenges;

import com.irunninglog.api.challenges.IChallenge;
import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.security.IUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengesService implements IChallengesService {

    @Override
    public List<IChallenge> getChallenges(IUser user) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
