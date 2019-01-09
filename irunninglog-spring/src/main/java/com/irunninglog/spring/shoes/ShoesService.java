package com.irunninglog.spring.shoes;

import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.spring.strava.StravaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class ShoesService implements IShoesService {

    private final StravaApiService stravaApiService;

    @Autowired
    ShoesService(StravaApiService stravaApiService) {

        super();

        this.stravaApiService = stravaApiService;
    }

    @Override
    public List<IShoe> getShoes(IUser user) {
        return stravaApiService.shoes(user);
    }

}
