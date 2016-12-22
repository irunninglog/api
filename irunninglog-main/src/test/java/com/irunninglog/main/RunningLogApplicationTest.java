package com.irunninglog.main;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class RunningLogApplicationTest {

    @Test
    public void sanity() throws IOException {
        System.setProperty("env", "file:///" + new ClassPathResource("application.properties").getFile().getAbsolutePath());

        new RunningLogApplication().start();

        assertTrue(Boolean.TRUE);
    }

}
