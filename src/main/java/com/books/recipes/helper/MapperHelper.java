package com.books.recipes.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperHelper {
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
