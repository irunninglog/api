package com.irunninglog.spring.data;

import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoeEntityTest extends AbstractTest {

    @Test
    public void sanity() {
        ShoeEntity shoeEntity = new ShoeEntity();
        shoeEntity.setStartDate(LocalDate.now());
        shoeEntity.setMax(500);

        assertNotNull(shoeEntity.getStartDate());
        assertEquals(500, shoeEntity.getMax(), 1E-9);
    }

}
