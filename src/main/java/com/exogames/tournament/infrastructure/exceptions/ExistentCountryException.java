package com.exogames.tournament.infrastructure.exceptions;

public class ExistentCountryException extends RuntimeException{
    public ExistentCountryException (String message){
        super(message);
    }
}
