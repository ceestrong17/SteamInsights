package com.ceestrong.steaminsights.exception;

public class NoGamesFoundException extends RuntimeException {
    public NoGamesFoundException(String message){
        super(message);
    }
}
