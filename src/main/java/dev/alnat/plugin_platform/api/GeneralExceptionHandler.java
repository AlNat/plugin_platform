package dev.alnat.plugin_platform.api;

import dev.alnat.plugin_platform.plugin.exception.PluginProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by @author AlNat on 02.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = PluginProcessingException.class)
    protected ResponseEntity<Object> handlePluginException(PluginProcessingException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Exception at plugin processing", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
