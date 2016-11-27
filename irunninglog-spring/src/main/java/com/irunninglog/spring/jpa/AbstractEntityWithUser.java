package com.irunninglog.spring.jpa;

import com.irunninglog.spring.security.impl.UserEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("unused")
public abstract class AbstractEntityWithUser extends AbstractEntity {

    @ManyToOne(optional=false)
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
