package com.quangnv.msb.configuration.staticContext;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class StaticContextAccessor {
    private static final Map<Class, DynamicInvocationHandler> classHandlers = new HashMap<>();
    private static ApplicationContext context;

    @Autowired
    public StaticContextAccessor(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            return getProxy(clazz);
        }
        return context.getBean(clazz);
    }

    private static <T> T getProxy(Class<T> clazz) {
        DynamicInvocationHandler<T> dynamicHandler = new DynamicInvocationHandler<>();
        classHandlers.put(clazz, dynamicHandler);
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                dynamicHandler
        );
    }

    //Use the context to get the actual beans and feed them to the invocation handlers
    @PostConstruct
    private void init() {
        classHandlers.forEach((clazz, dynamicHandler) -> {
            Object bean = context.getBean(clazz);
            dynamicHandler.setActualBean(bean);
        });
    }

    @Setter
    static class DynamicInvocationHandler<T> implements InvocationHandler {
        private T actualBean;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (actualBean == null) {
                throw new RuntimeException("Not initialized yet!");
            }
            return method.invoke(actualBean, args);
        }
    }
}