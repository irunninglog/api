package com.irunninglog.api.profile.impl;

import org.springframework.data.jpa.repository.JpaRepository;

interface IProfileRepository extends JpaRepository<ProfileEntity, Long> {

}
