package com.ssafy.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@Api(value = "인증 API", tags = {"Auth."})
@RestController
public class AuthController {
    @GetMapping("/auth")
    public ResponseEntity<?> Helloworld(){

        return ResponseEntity.status(200).body("Hello World");
    }
}
