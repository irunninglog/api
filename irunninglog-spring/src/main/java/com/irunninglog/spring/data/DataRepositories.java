package com.irunninglog.spring.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataRepositories {

    private final IRouteEntityRespository routeEntityRespository;
    private final IRunEntityRepository runEntityRepository;
    private final IShoeEntityRepository shoeEntityRepository;

    @Autowired
    public DataRepositories(IRouteEntityRespository routeEntityRespository,
                            IRunEntityRepository runEntityRepository,
                            IShoeEntityRepository shoeEntityRepository) {

        this.routeEntityRespository = routeEntityRespository;
        this.runEntityRepository = runEntityRepository;
        this.shoeEntityRepository = shoeEntityRepository;
    }

    public IRouteEntityRespository getRouteEntityRespository() {
        return routeEntityRespository;
    }

    public IRunEntityRepository getRunEntityRepository() {
        return runEntityRepository;
    }

    public IShoeEntityRepository getShoeEntityRepository() {
        return shoeEntityRepository;
    }

}
