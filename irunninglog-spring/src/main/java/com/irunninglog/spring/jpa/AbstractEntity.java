package com.irunninglog.spring.jpa;

import javax.persistence.*;


@MappedSuperclass
@SuppressWarnings("unused")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
