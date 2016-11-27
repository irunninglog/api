package com.irunninglog.security.impl;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user_entity")
public class UserEntity {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy= GenerationType.TABLE)
    private long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "user_entity_authorities")
    private Collection<AuthorityEntity> authorities;


    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

}
