package com.quangnv.msb.core.body;

import com.quangnv.msb.configuration.api.ApiAction;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestBody {
    private ApiAction action;
    private Object payload;
}
