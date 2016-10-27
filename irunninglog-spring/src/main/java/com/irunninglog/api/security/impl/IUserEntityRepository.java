package com.irunninglog.api.security.impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserEntityRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
