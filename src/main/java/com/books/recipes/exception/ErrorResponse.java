package com.books.recipes.exception;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@JsonRootName("Error")
@Data
@RequiredArgsConstructor
public class ErrorResponse {

    private final String message;
    private final List<String> details;
}
