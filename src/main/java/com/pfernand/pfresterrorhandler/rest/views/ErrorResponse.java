package com.pfernand.pfresterrorhandler.rest.views;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@JsonTypeName(value = "error")
@JsonTypeInfo(
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        use = JsonTypeInfo.Id.NAME
)
public class ErrorResponse {
    private int code;
    private String message;
    @Singular private List<ErrorValue> errors;
}
