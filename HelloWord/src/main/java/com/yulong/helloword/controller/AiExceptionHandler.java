package com.yulong.helloword.controller;

import com.openai.errors.OpenAIException;
import com.openai.errors.UnexpectedStatusCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AiExceptionHandler.class);

    @ExceptionHandler(UnexpectedStatusCodeException.class)
    public ResponseEntity<Map<String, Object>> handleOpenAiStatusError(UnexpectedStatusCodeException ex) {
        int statusCode = ex.statusCode();
        String responseBody = ex.body() != null ? ex.body().toString() : "";

        log.error("AI upstream error: status={}, body={}, message={}", statusCode, responseBody, ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "AI_UPSTREAM_ERROR");
        body.put("status", statusCode);
        body.put("message", ex.getMessage());
        body.put("upstreamBody", responseBody);

        HttpStatus status = HttpStatus.resolve(statusCode);
        return ResponseEntity.status(status != null ? status : HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(OpenAIException.class)
    public ResponseEntity<Map<String, Object>> handleOpenAiError(OpenAIException ex) {
        log.error("AI client error: {}", ex.getMessage(), ex);

        Map<String, Object> body = new HashMap<>();
        body.put("error", "AI_CLIENT_ERROR");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }
}
