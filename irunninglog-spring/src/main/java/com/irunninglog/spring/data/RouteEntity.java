package com.irunninglog.spring.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="route_entity")
// Needs to be public
public final class RouteEntity extends AbstractDataEntity {

    public RouteEntity() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
