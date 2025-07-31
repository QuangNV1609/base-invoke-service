package com.quangnv.msb.controller;

import com.quangnv.msb.configuration.api.InvocationService;
import com.quangnv.msb.core.body.ApplicationRequestBody;
import com.quangnv.msb.utils.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/invoke")
public class InvocationController extends AbstractBasedController {

    private final InvocationService invocationService;

    protected InvocationController(ResponseCreator responseCreator, InvocationService invocationService) {
        super(responseCreator);
        this.invocationService = invocationService;
    }

    @PostMapping
    public ResponseEntity<?> invoke(@RequestBody ApplicationRequestBody requestBody) throws InvocationTargetException, IllegalAccessException {
        Object response = invocationService.run(requestBody);
        return ResponseEntity.ok(responseCreator.success(response));
    }
}