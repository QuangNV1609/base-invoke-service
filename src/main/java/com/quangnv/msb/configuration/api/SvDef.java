package com.quangnv.msb.configuration.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SvDef {
    private Class<?> clazz;
    private String method;
    private Class<?> payloadClazz;
}
