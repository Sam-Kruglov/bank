package com.samkruglov.bank.api.rest.config;

import com.samkruglov.bank.api.rest.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Objects.nonNull;

@ControllerAdvice(basePackageClasses = UserController.class)
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<String> illegalArgument(IllegalArgumentException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> everything(Exception e) {

        Throwable last = getRootCause(e);
        LOG.error(last.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(last.getMessage());
    }

    private Throwable getRootCause(Exception e) {
        Throwable last = e;
        while (nonNull(last.getCause())) {
            last = e.getCause();
        }
        return last;
    }
}
