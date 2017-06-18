package com.irunninglog.spring.mapper;

import com.irunninglog.api.IRequest;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class MapperTest extends AbstractTest {

    private IRequest request;
    private IMapper mapper;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        mapper = applicationContext.getBean(IMapper.class);
        request = applicationContext.getBean(IRequest.class);
    }

    @Test
    public void encodeSuccess() {
        assertNotNull(mapper.encode(request));
    }

    @Test
    public void encodeFailure() {
        try {
            mapper.encode(new Object());
            fail("Should have thrown");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Can't encode"));
        }
    }

    @Test
    public void decodeSuccess() {
        assertNotNull(mapper.decode("{}", IRequest.class));
    }

    @Test
    public void decodeFailure() {
        try {
            mapper.decode("{\"foo\":\"bar\"}", IRequest.class);
            fail("Should have thrown");
        } catch (IllegalArgumentException ex){
            assertTrue(ex.getMessage().contains("Can't decode"));
        }
    }

}
