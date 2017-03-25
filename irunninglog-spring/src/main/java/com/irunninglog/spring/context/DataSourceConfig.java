package com.irunninglog.spring.context;

import com.google.common.base.MoreObjects;

final class DataSourceConfig {

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;

    DataSourceConfig(String config) {
        super();

        String [] tokens = config.split("\\|");
        driverClassName = tokens[0];
        url = tokens[1];
        username = tokens[2];
        password = tokens.length > 3 ? tokens[3] : "";
    }

    String getDriverClassName() {
        return driverClassName;
    }

    String getUrl() {
        return url;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("driverClassName", driverClassName)
                .add("url", url)
                .add("username", username)
                .toString();
    }

}
