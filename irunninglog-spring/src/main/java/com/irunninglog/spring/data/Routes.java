package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IRoute;
import com.irunninglog.api.data.IRoutes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class Routes implements IRoutes{

    private final List<IRoute> routes = new ArrayList<>();

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Route.class)
    public List<IRoute> getRoutes() {
        return routes;
    }

}
