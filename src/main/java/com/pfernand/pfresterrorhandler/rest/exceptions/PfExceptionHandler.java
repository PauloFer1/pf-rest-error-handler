package com.pfernand.pfresterrorhandler.rest.exceptions;

import com.pfernand.pfresterrorhandler.rest.views.ErrorResponse;
import com.pfernand.pfresterrorhandler.rest.views.ErrorValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class PfExceptionHandler {

    private static final String UNEXPECTED_MAILLER_ERROR = "Unexpected error";


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(
            final Exception ex,
            final WebRequest request
    ) {
        return respond(
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex,
                UNEXPECTED_MAILLER_ERROR
        );
    }

    private ResponseEntity<ErrorResponse> respond(
        final WebRequest request,
        final HttpStatus httpStatus,
        final Exception error,
        final String message
    ) {
        log.error(String.format("%s %s", request, error));

        StackTraceElement topError = error.getStackTrace()[0];
        ErrorValue.ErrorValueBuilder errorValueBuilder = ErrorValue.builder()
            .domain(topError.getClassName())
            .target(error.getMessage());
        if (error.getCause() != null) {
            errorValueBuilder.reason(error.getCause().getMessage());
        }
        return ResponseEntity.status(httpStatus).body(
            ErrorResponse.builder()
                .code(httpStatus.value())
                .error(errorValueBuilder.build())
                .message(message)
                .build());
    }
}
