package com.irunninglog.spring.shoes;

import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.api.shoes.IShoesService;
import com.irunninglog.spring.strava.StravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class ShoesService implements IShoesService {

    private final StravaService stravaService;

    @Autowired
    ShoesService(StravaService stravaService) {

        super();

        this.stravaService = stravaService;
    }

    @Override
    public List<IShoe> getShoes(IUser user) {
        return stravaService.shoes(user);
    }

}
