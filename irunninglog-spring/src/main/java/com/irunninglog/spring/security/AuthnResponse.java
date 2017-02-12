package com.irunninglog.spring.security;

import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AuthnResponse extends AbstractResponse<IUser, AuthnResponse> implements IAuthnResponse<AuthnResponse> {

}
