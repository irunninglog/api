package com.irunninglog.spring.data.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SuppressWarnings("JpaQlInspection")
public interface IShoeEntityRepository extends CrudRepository<ShoeEntity, Long> {

    @Query(value = "select s from ShoeEntity s where s.user.id = :id and s.dashboard = true")
    List<ShoeEntity> dashboardShoes(@Param("id") long id);

}
