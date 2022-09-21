package com.books.recipes.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@RequiredArgsConstructor
public class ResourceAlreadyPresent extends RuntimeException {

    private final String context;

    @Override
    public String getMessage() {
        return String.format("%s is already present in our system", context);
    }
}
