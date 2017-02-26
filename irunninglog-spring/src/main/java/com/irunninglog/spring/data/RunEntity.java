package com.irunninglog.spring.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="run_entity")
// Needs to be public
final public class RunEntity extends AbstractDataEntity {

    public RunEntity() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
