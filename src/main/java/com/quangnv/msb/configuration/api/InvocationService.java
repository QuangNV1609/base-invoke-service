package com.quangnv.msb.configuration.api;

import com.quangnv.msb.core.body.ApplicationRequestBody;
import com.quangnv.msb.core.utils.JsonHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
@RequiredArgsConstructor
public class InvocationService {

    private final APIFactory apiFactory;
    private final ApplicationContext applicationContext;

    public Object run(ApplicationRequestBody requestBody) throws InvocationTargetException, IllegalAccessException {
        SvDef svDef = apiFactory.getInvoker(requestBody.getAction());
        String methodName = svDef.getMethod();
        if (svDef.getPayloadClazz() != null) {
            Method method = ReflectionUtils.findMethod(svDef.getClazz(), methodName, svDef.getPayloadClazz());
            assert method != null;
            return method.invoke(applicationContext.getBean(svDef.getClazz()), JsonHelpers.convert(requestBody.getPayload(), svDef.getPayloadClazz()));
        }
        Method method = ReflectionUtils.findMethod(svDef.getClazz(), methodName);
        assert method != null;
        return method.invoke(applicationContext.getBean(svDef.getClazz()));
    }

}