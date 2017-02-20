package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IShoe;
import com.irunninglog.api.data.IShoes;

import java.util.ArrayList;
import java.util.List;

public class MockShoes implements IShoes {

    private final List<IShoe> shoes = new ArrayList<>();

    @Override
    public List<IShoe> getShoes() {
        return shoes;
    }

}
