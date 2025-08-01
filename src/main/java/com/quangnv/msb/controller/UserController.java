package com.quangnv.msb.controller;

import com.quangnv.msb.core.dto.auth.AuthRequestDTO;
import com.quangnv.msb.core.dto.auth.AuthResponseDTO;
import com.quangnv.msb.facade.UserFacade;
import com.quangnv.msb.utils.ResponseCreator;
import com.quangnv.msb.utils.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/public/users")
@RestController("publicUserController")
public class UserController extends AbstractBasedController {

    private final UserFacade userFacade;

    protected UserController(ResponseCreator responseCreator, UserFacade userFacade) {
        super(responseCreator);
        this.userFacade = userFacade;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<StandardResponse> authenticate(@Valid @RequestBody AuthRequestDTO payload) {
        AuthResponseDTO data = userFacade.authenticate(payload);
        return ResponseEntity.ok(responseCreator.success(data));
    }
}