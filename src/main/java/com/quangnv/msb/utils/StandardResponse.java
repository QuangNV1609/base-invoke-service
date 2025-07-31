package com.quangnv.msb.utils;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardResponse {
    private String code;
    private String desc;
    private Object data;
    private Object extraInfo;
    private String tokenKey;
    private String errorTokenKey;
    private Long requestTime;
    private Long responseTime;
    private String serviceId;
    private String clientIP;
}
