package com.quangnv.msb.utils;

import com.quangnv.msb.configuration.message.MessageTranslator;
import com.quangnv.msb.configuration.token.TokenKeyIssuer;
import com.quangnv.msb.configuration.web.MDCContextHolder;
import com.quangnv.msb.configuration.web.MDCKey;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.core.utils.AppConstants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResponseCreator {
    private final TokenKeyIssuer tokenKeyIssuer;
    private final MessageTranslator messageTranslator;

    public ResponseCreator(@Qualifier("UUIDTokenKeyIssuer") TokenKeyIssuer tokenKeyIssuer, MessageTranslator messageTranslator) {
        this.tokenKeyIssuer = tokenKeyIssuer;
        this.messageTranslator = messageTranslator;
    }

    public StandardResponse createResponse(String code, String desc, Object data, Object extraInfo) {
        StandardResponse response = new StandardResponse();
        String descMsg = Maybe.of(desc).orElse(() -> messageTranslator.get(code));
        response.setCode(code);
        response.setDesc(descMsg);
        response.setData(data);
        response.setServiceId(AppConstants.SERVICE_CODE);
        response.setTokenKey(MDCContextHolder.getTokenKey());
        if (!MAErrorCode.SUCCESS.equals(code)) {
            MDCContextHolder.setContext(MDCKey.ETK, tokenKeyIssuer.issue());
        }
        response.setErrorTokenKey(MDCContextHolder.getErrorTokenKey());
        Maybe.of(MDCContextHolder::getRequestTime)
                .map(Long::valueOf)
                .peek(response::setRequestTime);
        response.setClientIP(MDCContextHolder.getClientIP());
        response.setExtraInfo(extraInfo);
        response.setResponseTime(Instant.now().toEpochMilli());
        return response;
    }

    public StandardResponse success(Object data) {
        return createResponse(MAErrorCode.SUCCESS, null, data, null);
    }

    public StandardResponse error(Object extraInfo) {
        return createResponse(MAErrorCode.FAILED, null, null, extraInfo);

    }

    public StandardResponse badRequest(String message) {
        String desc = messageTranslator.get(MAErrorCode.INVALID_DATA_FORMAT);
        String msg = messageTranslator.get(message);
        return createResponse(MAErrorCode.INVALID_DATA_FORMAT, desc, null, msg);
    }


    public StandardResponse error(String code, String desc, Object data, Object extraInfo) {
        return createResponse(code, desc, data, extraInfo);
    }

    public StandardResponse error(String code, Object extraInfo) {
        return createResponse(code, null, null, extraInfo);
    }

    public StandardResponse error(UnExpectedException ex) {
        Throwable rootCause = Maybe.of(ExceptionUtils.getRootCause(ex)).orElse(ex);
        String code = ex.getCode();
        Object data = ex.getData();
        String desc = messageTranslator.get(code, data);
        Object extraInfo = Maybe.of(data).orElse(rootCause.getMessage());
        return createResponse(code, desc, data, extraInfo);
    }
}
