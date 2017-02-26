package com.irunninglog.spring.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Needs to be public
public interface IRunEntityRepository extends CrudRepository<RunEntity, Long> {

    List<RunEntity> findByProfileId(long id);

}
