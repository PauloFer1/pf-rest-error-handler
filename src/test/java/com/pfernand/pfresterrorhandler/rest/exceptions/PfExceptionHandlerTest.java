package com.pfernand.pfresterrorhandler.rest.exceptions;


import com.pfernand.pfresterrorhandler.rest.views.ErrorResponse;
import com.pfernand.pfresterrorhandler.rest.views.ErrorValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public class PfExceptionHandlerTest {

    private final static PfExceptionHandler pfExceptionHandler = new PfExceptionHandler();

    @Test
    public void exceptionHandlerReturnsEntity() {
        // Given
        Exception ex = new RuntimeException("Opss!");
        ErrorValue errorValue = ErrorValue.builder()
                .domain(ex.getStackTrace()[0].getClassName())
                .target(ex.getMessage())
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(errorValue)
                .message("Unexpected error")
                .build();
        WebRequest webRequest = null;

        // When
        ResponseEntity<ErrorResponse> response = pfExceptionHandler.exceptionHandler(ex, webRequest);

        // Then
        Assertions.assertEquals(errorResponse, response.getBody());
    }

}
