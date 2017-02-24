package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IProgressInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgressInfoComparatorTest {

    @Test
    public void test1() {
        IProgressInfo one = new ProgressInfo().setPercentage(10);
        IProgressInfo two = new ProgressInfo().setPercentage(20);

        assertEquals(1, new ProgressInfoComparator().compare(one, two));
    }

    @Test
    public void test2() {
        IProgressInfo one = new ProgressInfo().setPercentage(10).setMax(100);
        IProgressInfo two = new ProgressInfo().setPercentage(10).setMax(200);

        assertEquals(-1, new ProgressInfoComparator().compare(one, two));
    }

}
