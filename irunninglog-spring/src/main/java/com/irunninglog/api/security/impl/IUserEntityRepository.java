package com.irunninglog.api.security.impl;

import org.springframework.data.repository.CrudRepository;

public interface IUserEntityRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
