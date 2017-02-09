package com.irunninglog.spring.factory;

import com.irunninglog.api.factory.IFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Factory implements IFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public <T> T get(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
