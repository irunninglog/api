package com.irunninglog.api;

import java.util.Map;

public interface IRequest {

    IRequest setMap(Map<String, Object> map);

    Map<String, Object> getMap();

}
