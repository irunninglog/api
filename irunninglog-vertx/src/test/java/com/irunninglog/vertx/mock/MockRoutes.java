package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IRoute;
import com.irunninglog.api.data.IRoutes;

import java.util.ArrayList;
import java.util.List;

public class MockRoutes implements IRoutes {

    private final List<IRoute> routes = new ArrayList<>();

    @Override
    public List<IRoute> getRoutes() {
        return routes;
    }

}
