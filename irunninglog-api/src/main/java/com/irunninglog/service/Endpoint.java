package com.irunninglog.service;

import com.irunninglog.security.AccessControl;

public enum Endpoint {

    GetDashboard("c83068b3-2d60-4578-b981-a6c173441ba4",
            "/profiles/:profileid/dashboard",
            AccessControl.AllowProfile,
            EndpointMethod.GET),
    GetProfile("fff11faa-bf42-442c-ac31-eb338c7cf81e",
            "/profiles/:profileid",
            AccessControl.AllowProfile,
            EndpointMethod.GET),
    Ping("6678d445-030f-4aad-b360-6304588c07b6",
            "/ping",
            AccessControl.AllowAll,
            EndpointMethod.GET);

    private final String address;
    private final String path;
    private final AccessControl control;
    private final EndpointMethod method;

    Endpoint(String address, String path, AccessControl control, EndpointMethod method) {
        this.address = address;
        this.path = path;
        this.control = control;
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public String getPath() {
        return path;
    }

    public AccessControl getControl() {
        return control;
    }

    public EndpointMethod getMethod() {
        return method;
    }

}
