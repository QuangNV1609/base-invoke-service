package com.quangnv.msb.configuration.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpClient {

    private static final class SingletonHolder {
        private static final HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static OkHttpClient client = null;

    public OkHttpClient initOkHttpClient() {
        return initOkHttpClient(5L, 5L, 5L);
    }

    public OkHttpClient initOkHttpClient(long connectTimeout, long readTimeout, long writeTimeout) {
        try {
            if (client == null) {
                OkHttpClient clientOrig = (new OkHttpClient.Builder())
                        .retryOnConnectionFailure(true)
                        .cache(new Cache(new File("/"), 2097152L))
                        .build();
                client = clientOrig.newBuilder()
                        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                        .readTimeout(readTimeout, TimeUnit.SECONDS)
                        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                        .connectionPool(new ConnectionPool())
                        .build();
                return client;
            }
        } catch (Exception ex) {
            log.error(">>>===REQUEST-TRACE===Function initOkHttpClient have ex:", ex);
        }
        return client;
    }

}
