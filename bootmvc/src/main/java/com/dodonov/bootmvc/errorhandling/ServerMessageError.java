package com.dodonov.bootmvc.errorhandling;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;


public record ServerMessageError (
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {

}
