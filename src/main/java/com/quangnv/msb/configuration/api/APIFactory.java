package com.quangnv.msb.configuration.api;

import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import com.quangnv.msb.core.funtional.Maybe;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class APIFactory {

    private Map<ApiAction, SvDef> SV_DEF = new HashMap<>();

    public void regisInvoker(ApiAction action, SvDef o) {
        SV_DEF.put(action, o);
    }

    public SvDef getInvoker(ApiAction action) {
        return Maybe.of(action)
                .map(SV_DEF::get)
                .orElseSupplyException(() -> new UnExpectedException(MAErrorCode.INVALID_API));
    }
}
