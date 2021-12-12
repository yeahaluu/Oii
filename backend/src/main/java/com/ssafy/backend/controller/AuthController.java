package com.ssafy.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * ���� ���� API ��û ó���� ���� ��Ʈ�ѷ� ����.
 */

@Api(value = "���� API", tags = {"Auth."})
@RestController
public class AuthController {
    @GetMapping("/auth")
    public ResponseEntity<?> Helloworld(){

        return ResponseEntity.status(200).body("Hello World");
    }
}
