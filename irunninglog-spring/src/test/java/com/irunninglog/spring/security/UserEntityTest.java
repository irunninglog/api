package com.irunninglog.spring.security;

import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class UserEntityTest extends AbstractTest {

    @Test
    public void sanity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("foo");
        userEntity.setPassword("bar");
        userEntity.setAuthorities(Collections.singletonList(new AuthorityEntity().setId(1).setName("foobar")));

        assertEquals(1, userEntity.getId());
        assertEquals("foo", userEntity.getUsername());
        assertEquals("bar", userEntity.getPassword());
        assertEquals(1, userEntity.getAuthorities().iterator().next().getId());
        assertEquals("foobar", userEntity.getAuthorities().iterator().next().getName());
    }

}
