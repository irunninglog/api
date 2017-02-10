package com.irunninglog.api.security;

import java.util.List;

public interface ILogin {

    ILogin setId(long id);

    ILogin setName(String username);

    ILogin setRoles(List<String> authorities);

}
