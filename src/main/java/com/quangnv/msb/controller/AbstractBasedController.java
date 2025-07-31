package com.quangnv.msb.controller;

import com.quangnv.msb.utils.ResponseCreator;

public abstract class AbstractBasedController {
    protected final ResponseCreator responseCreator;

    protected AbstractBasedController(ResponseCreator responseCreator) {
        this.responseCreator = responseCreator;
    }
}
