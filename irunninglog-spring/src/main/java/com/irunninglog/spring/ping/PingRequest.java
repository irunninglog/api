package com.irunninglog.spring.ping;

import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.spring.AbstractRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class PingRequest extends AbstractRequest<PingRequest> implements IPingRequest<PingRequest> {

}
