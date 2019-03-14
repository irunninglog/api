package com.irunninglog.spring.streaks;

import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StreakTest extends AbstractTest {

    @Test
    public void toStringTest() {
        assertTrue(new Streak().toString().contains("percentage"));
    }

}
