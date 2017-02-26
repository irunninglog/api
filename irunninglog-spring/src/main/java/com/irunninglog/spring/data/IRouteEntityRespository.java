package com.irunninglog.spring.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Needs to be public
public interface IRouteEntityRespository extends CrudRepository<RouteEntity, Long> {

    List<RouteEntity> findByProfileId(long id);

}
