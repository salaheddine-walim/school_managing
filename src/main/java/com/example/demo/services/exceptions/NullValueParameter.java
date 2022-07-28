package com.example.demo.services.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NullValueParameter extends RuntimeException {

    public NullValueParameter(String message) {
        super(message);
    }
}
