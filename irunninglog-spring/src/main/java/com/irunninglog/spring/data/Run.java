package com.irunninglog.spring.data;

import com.irunninglog.api.data.IRun;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Run extends AbstractData<IRun> implements IRun {

    public Run() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
