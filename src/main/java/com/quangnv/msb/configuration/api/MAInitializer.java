package com.quangnv.msb.configuration.api;

import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.facade.Facade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class MAInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(MAInitializer.class);
    private final APIFactory apiFactory;
    private final ApplicationContext applicationContext;

    public MAInitializer(APIFactory apiFactory, ApplicationContext applicationContext) {
        this.apiFactory = apiFactory;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Collection<Object> facades = applicationContext.getBeansWithAnnotation(Facade.class)
                .values();
        facades.forEach(f -> Maybe.of(f).map(Object::getClass).peek(clazz -> {
            Method[] methods = clazz.getDeclaredMethods();
            Arrays.stream(methods).forEach(method -> {
                MetaFlow annotation = AnnotationUtils.findAnnotation(method, MetaFlow.class);
                if (annotation != null) {
                    List<Class<?>> parameterTypes = Arrays.asList(method.getParameterTypes());
                    SvDef svDef = SvDef.builder()
                            .clazz(clazz)
                            .method(method.getName())
                            .payloadClazz(parameterTypes.isEmpty() ? null : parameterTypes.get(0))
                            .build();
                    apiFactory.regisInvoker(annotation.action(), svDef);
                }
            });
        }));
    }
}
