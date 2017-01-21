package com.irunninglog.spring.jpa;

import com.irunninglog.spring.profile.impl.ProfileEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("unused")
public abstract class AbstractEntityWithProfile extends AbstractEntity {

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_id")
    private ProfileEntity profile;

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

}
