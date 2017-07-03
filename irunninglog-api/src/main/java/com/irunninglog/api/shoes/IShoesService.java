package com.irunninglog.api.shoes;

import com.irunninglog.api.security.IUser;

import java.util.List;

public interface IShoesService {

    List<IShoe> getShoes(IUser user);

}
