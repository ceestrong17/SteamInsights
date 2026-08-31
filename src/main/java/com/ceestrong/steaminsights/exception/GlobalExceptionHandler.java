package com.ceestrong.steaminsights.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handlePlayerNotFoundException(PlayerNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoGamesFoundException.class)
    public ResponseEntity<String> handleNoGamesFoundException(NoGamesFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSteamIdException.class)
    public ResponseEntity<String> handleInvalidSteamIdException(InvalidSteamIdException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppDetailsNotFoundException.class)
    public ResponseEntity<String> handleAppDetailsNotFoundException(AppDetailsNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
