package com.books.recipes.exception;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonRootName("Error")
@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String message;
    private final List<String> details;
}
