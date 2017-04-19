package com.irunninglog.spring.profile;

import org.springframework.data.repository.CrudRepository;

public interface IProfileEntityRepository extends CrudRepository<ProfileEntity, Long> {

    ProfileEntity findByUsername(String username);

}
