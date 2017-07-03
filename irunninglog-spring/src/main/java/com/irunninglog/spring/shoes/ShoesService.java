package com.irunninglog.spring.shoes;

import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class ShoesService implements IShoesService {

    private final IStravaService stravaService;

    @Autowired
    ShoesService(IStravaService stravaService) {
        this.stravaService = stravaService;
    }

    @Override
    public List<IShoe> getShoes(IUser user) {
        // TODO - Sort shoes by primary, distance, retired
        return stravaService.shoes(user);
    }

}
