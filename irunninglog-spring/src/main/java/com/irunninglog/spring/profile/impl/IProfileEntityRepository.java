package com.irunninglog.spring.profile.impl;

import org.springframework.data.repository.CrudRepository;

public interface IProfileEntityRepository extends CrudRepository<ProfileEntity, Long> {

    ProfileEntity findByEmail(String email);

}
