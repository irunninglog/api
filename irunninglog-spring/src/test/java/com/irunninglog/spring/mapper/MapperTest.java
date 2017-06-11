package com.irunninglog.spring.mapper;

import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class MapperTest extends AbstractTest {

    private IMapper mapper;
    private ApplicationContext applicationContext;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        mapper = applicationContext.getBean(IMapper.class);
        this.applicationContext = applicationContext;
    }

    @Test
    public void encode() {
        assertNotNull(mapper.encode(applicationContext.getBean(IProfile.class)));
    }

    @Test
    public void encodeError() {
        try {
            mapper.encode(Mockito.mock(IProfile.class));
            fail("Should have failed");
        } catch (IllegalArgumentException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

    @Test
    public void decode() {
        assertNotNull(mapper.decode("{\"id\": 1}", IProfile.class));
    }

    @Test
    public void decodeError() {
        try {
            mapper.decode("foo", IProfile.class);
            fail("Should have failed");
        } catch (Exception ex) {
            assertTrue(Boolean.TRUE);
        }
    }

}
