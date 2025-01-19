package com.github.allex_goncalves.Forum.Hub.Api.Infra.exceptions;

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }
}
