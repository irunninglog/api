package com.irunninglog.spring.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="irl_entity_route")
public final class RouteEntity extends AbstractDataEntity {

    public RouteEntity() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
