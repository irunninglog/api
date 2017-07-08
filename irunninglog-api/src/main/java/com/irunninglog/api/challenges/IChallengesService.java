package com.irunninglog.api.challenges;

import com.irunninglog.api.security.IUser;

import java.util.List;

public interface IChallengesService {

    List<IChallenge> getChallenges(IUser user);

}
