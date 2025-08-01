package com.quangnv.msb.core.dto.auth;

import com.quangnv.msb.core.token.TokenDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private TokenDTO accessToken;
    private String role;
    private UserAuthResponseDTO user;
}
