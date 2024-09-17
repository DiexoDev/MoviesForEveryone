package com.diexoDev.MoviesForEveryone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String mensaje) {
        super(mensaje);
    }

    public FileNotFoundException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }
}
