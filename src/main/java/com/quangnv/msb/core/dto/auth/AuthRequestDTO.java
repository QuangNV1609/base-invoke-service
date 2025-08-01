package com.quangnv.msb.core.dto.auth;

import com.quangnv.msb.utils.validation.NotEmptyField;
import com.quangnv.msb.utils.validation.NotNullField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    @NotEmptyField(name = "username")
    private String username;
    private String password;
    private String fireBaseToken;
    @NotNullField(name = "loginType")
    private LoginType loginType;
}
