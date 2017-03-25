package com.irunninglog.spring.context;

import com.google.common.base.MoreObjects;

final class JpaConfig {

    private final String autoDdl;
    private final String dialect;
    private final String showSql;

    JpaConfig(String config) {
        super();

        String [] tokens = config.split("\\|");
        autoDdl = tokens[0];
        dialect = tokens[1];
        showSql = tokens.length > 2 ? tokens[2] : Boolean.FALSE.toString();
    }

    String getAutoDdl() {
        return autoDdl;
    }

    String getDialect() {
        return dialect;
    }

    String getShowSql() {
        return showSql;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("autoDdl", autoDdl)
                .add("dialect", dialect)
                .add("showSql", showSql)
                .toString();
    }

}
