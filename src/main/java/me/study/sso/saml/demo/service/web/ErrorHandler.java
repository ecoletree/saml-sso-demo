/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 *
 * Author : HyungSeok Kim
 * Create Date : 2024. 01. 02.
 * File Name : ErrorHandler.java
 * DESC :
 *****************************************************************/
package me.study.sso.saml.demo.service.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestControllerAdvice
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception e) {
        log.error("Unhandled Error occurred", e);
        return "error";
    }

}
