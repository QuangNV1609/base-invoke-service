package com.quangnv.msb.core.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String value;
    private int timeToLiveInMinutes;
}
