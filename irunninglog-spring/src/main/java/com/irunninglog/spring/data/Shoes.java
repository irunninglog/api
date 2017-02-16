package com.irunninglog.spring.data;

import com.irunninglog.api.data.IShoe;
import com.irunninglog.api.data.IShoes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class Shoes implements IShoes {

    private final List<IShoe> shoes = new ArrayList<>();

    @Override
    public List<IShoe> getShoes() {
        return shoes;
    }

}
