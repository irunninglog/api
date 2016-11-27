package com.irunninglog.dashboard;

import com.irunninglog.Progress;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProgressInfoTest {

    private ProgressInfo progressInfo;

    @Before
    public void before() {
        progressInfo = new ProgressInfo()
                .setTitle("title")
                .setSubTitle("sub")
                .setTextOne("one")
                .setTextTwo("two")
                .setMax(200)
                .setValue(10)
                .setPercentage(5)
                .setProgress(Progress.Bad);
    }

    @Test
    public void getters() {
        assertEquals("title", progressInfo.getTitle());
        assertEquals("sub", progressInfo.getSubTitle());
        assertEquals("one", progressInfo.getTextOne());
        assertEquals("two", progressInfo.getTextTwo());
        assertEquals(200, progressInfo.getMax());
        assertEquals(10, progressInfo.getValue());
        assertEquals(5, progressInfo.getPercentage());
        assertEquals(Progress.Bad, progressInfo.getProgress());
    }

    @Test
    public void tostring() {
        String s = progressInfo.toString();
        assertTrue(s.contains("title"));
        assertTrue(s.contains("subTitle"));
        assertTrue(s.contains("textOne"));
        assertTrue(s.contains("textTwo"));
        assertTrue(s.contains("max"));
        assertTrue(s.contains("value"));
        assertTrue(s.contains("percentage"));
        assertTrue(s.contains("progress"));
    }

}
