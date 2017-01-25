package com.irunninglog.spring.data.impl;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRouteEntityRespository extends CrudRepository<RouteEntity, Long> {

    List<RouteEntity> findByProfileId(long id);

}
