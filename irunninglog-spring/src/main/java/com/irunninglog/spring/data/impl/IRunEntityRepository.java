package com.irunninglog.spring.data.impl;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRunEntityRepository extends CrudRepository<RunEntity, Long> {

    List<RunEntity> findByProfileId(long id);

}
