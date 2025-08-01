package com.quangnv.msb.core.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthResponseDTO {
    private Long id;
    private String fullName;
    @JsonProperty("isInternalUser")
    private boolean isInternalUser;
}
