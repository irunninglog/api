package com.irunninglog.spring.ping;

import com.irunninglog.api.ping.IPing;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class PingResponse extends AbstractResponse<IPing, PingResponse> implements IPingResponse<PingResponse> {

}
