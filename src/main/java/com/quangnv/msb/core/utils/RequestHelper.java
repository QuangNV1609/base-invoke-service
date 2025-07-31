package com.quangnv.msb.core.utils;

import com.quangnv.msb.core.funtional.Maybe;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class RequestHelper {
    private static final String[] IP_HEADER_CANDIDATES = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    public static String getClientIpAddress(final HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public static void sendError(final HttpServletResponse response, String content, final HttpStatus status) throws IOException {
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.setContentLength(content.length());
        response.getWriter().write(content);
    }

    public static byte[] getBody(HttpServletRequest request) {
        return Maybe.of(request)
                .mapThrowingFunc(HttpServletRequest::getInputStream)
                .mapThrowingFunc(StreamUtils::copyToByteArray)
                .orElse(new byte[0]);
    }

    public static String getBody(HttpServletRequest request, Charset charset) {
        return Maybe.of(request).mapThrowingFunc(HttpServletRequest::getInputStream).mapThrowingFunc(stream -> StreamUtils.copyToString(stream, charset)).orElseNull();
    }

    public static <T> T getBody(HttpServletRequest request, Class<T> clazz) {
        byte[] body = getBody(request);
        return JsonHelpers.byte2Object(body, clazz);
    }


    public Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.putIfAbsent(name, request.getHeader(name));
        }
        return headers;
    }

    public Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String name : headerNames) {
            headers.putIfAbsent(name, response.getHeader(name));
        }
        return headers;
    }
}
