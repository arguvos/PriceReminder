package com.arguvos.pricereminder.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/healthcheck")
public class HealthCheckController {

    @Get()
    public String check() {
        return "ok";
    }
}
