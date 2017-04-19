package com.irunninglog.spring.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="irl_entity_run")
public final class RunEntity extends AbstractDataEntity {

    public RunEntity() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
