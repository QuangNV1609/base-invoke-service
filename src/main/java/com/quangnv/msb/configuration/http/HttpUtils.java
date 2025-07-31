package com.quangnv.msb.configuration.http;


import com.quangnv.msb.core.dto.KeyValueDTO;
import com.quangnv.msb.core.dto.okhttp.UploadFileResponse;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.core.utils.JsonHelpers;
import lombok.experimental.UtilityClass;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static byte[] call(Request request) {
        long start = Instant.now().toEpochMilli();
        try {
            if (log.isInfoEnabled()) {
                log.info(">>>===REQUEST-TRACE=== Forward:[{}-{}]", request.method(), request.url());
            }
            OkHttpClient okHttpClient = HttpClient.getInstance().initOkHttpClient();
            if (request.url().isHttps()) {
                if (log.isInfoEnabled()) {
                    log.info(">>>===REQUEST-TRACE===Case is https, trust all ssl");
                }
                okHttpClient = trustAllSslClient(okHttpClient);
            }
            Response response = okHttpClient.newCall(request).execute();
            return Maybe.of(response)
                    .map(Response::body)
                    .mapThrowingFunc(ResponseBody::bytes)
                    .orElse(new byte[0]);
        } catch (SocketTimeoutException | ConnectException e) {
            log.error(">>>===REQUEST-TRACE===Request timeout:", e);
            throw new UnExpectedException(MAErrorCode.TIME_OUT);
        } catch (Exception e) {
            log.error(">>>===REQUEST-TRACE===Call request with exception:", e);
            throw new RuntimeException(e);
        } finally {
            if (log.isInfoEnabled()) {
                long end = Instant.now().toEpochMilli();
                log.info(">>>===REQUEST-TRACE=== Request Start : {} End: {} with Duration :{} ms", start, end, end - start);
            }
        }
    }

    public static String callDirect(Request request) {
        byte[] resp = call(request);
        String respStr = new String(resp, StandardCharsets.UTF_8);
        log.info(">>>===REQUEST-TRACE=== response:[{}]", respStr);
        return respStr;
    }

    public static String post(String endpoint, Object body) {
        return post(endpoint, body, Collections.emptyMap());
    }

    public static String post(String endpoint, Object body, Map<String, String> headers) {
        return call(endpoint, body, headers, HttpMethod.POST);
    }

    public static String put(String endpoint, Object body, Map<String, String> headers) {
        return call(endpoint, body, headers, HttpMethod.PUT);
    }

    public static String call(String endpoint, Object body, Map<String, String> headers, HttpMethod method) {
        RequestBody requestBody = RequestBody.create(JsonHelpers.object2Byte(body), MediaType.parse("application/json"));
        Request.Builder builder = new Request.Builder()
                .url(endpoint);
        switch (method) {
            case POST: {
                builder.post(requestBody);
                break;
            }
            case PUT: {
                builder.put(requestBody);
                break;
            }
            case DELETE: {
                builder.delete(requestBody);
                break;
            }
            case PATCH: {
                builder.patch(requestBody);
                break;
            }
            default:break;
        }
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        Request request = builder.build();
        log.info(">>>===REQUEST-TRACE=== endpoint:[{}], body:[{}], header:[{}], method : {}",
                endpoint,
                JsonHelpers.object2JsonSafe(body),
                headers,
                method);
        return callDirect(request);
    }


    public static List<UploadFileResponse> postFormData(String endpoint, List<KeyValueDTO<String, Object>> map) throws IOException {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (KeyValueDTO<String, Object> pair : map) {
            String key = pair.getKey();
            Object value = pair.getValue();
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                RequestBody fileBody = RequestBody.create(file.getBytes(), MediaType.get(Objects.requireNonNull(file.getContentType())));
                builder.addFormDataPart(key, file.getOriginalFilename(), fileBody);
            } else {
                builder.addFormDataPart(key, Maybe.of(value).map(String::valueOf).orElseNull());
            }
        }
        Request request = new Request.Builder()
                .url(endpoint)
                .post(builder.build())
                .build();
        byte[] resp = call(request);
        return JsonHelpers.byte2ListObject(resp, UploadFileResponse.class);
    }


    public static OkHttpClient trustAllSslClient(OkHttpClient client) {
        log.warn(">>>===REQUEST-TRACE===Using the trustAllSslClient is highly discouraged and should not be used in Production!");
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(TRUST_ALL_SSL_SOCKET_FACTORY, (X509TrustManager) TRUST_ALL_CERTS[0]);
        builder.hostnameVerifier((hostname, session) -> true);
        return builder.build();
    }

    private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }};

    private static final SSLContext TRUST_ALL_SSL_CONTEXT;

    static {
        try {
            TRUST_ALL_SSL_CONTEXT = SSLContext.getInstance("SSL");
            TRUST_ALL_SSL_CONTEXT.init(null, TRUST_ALL_CERTS, new SecureRandom());
        } catch (NoSuchAlgorithmException | java.security.KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private static final SSLSocketFactory TRUST_ALL_SSL_SOCKET_FACTORY = TRUST_ALL_SSL_CONTEXT.getSocketFactory();

}
