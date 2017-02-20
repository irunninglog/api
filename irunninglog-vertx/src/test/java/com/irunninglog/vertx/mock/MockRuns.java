package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IRun;
import com.irunninglog.api.data.IRuns;

import java.util.ArrayList;
import java.util.List;

public class MockRuns implements IRuns {

    private final List<IRun> runs = new ArrayList<>();

    @Override
    public List<IRun> getRuns() {
        return runs;
    }

}
