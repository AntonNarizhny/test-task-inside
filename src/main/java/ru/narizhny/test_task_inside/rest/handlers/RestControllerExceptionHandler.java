package ru.narizhny.test_task_inside.rest.handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "ru.narizhny.test_task_inside.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}