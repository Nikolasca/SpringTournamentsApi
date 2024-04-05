package com.exogames.countryService.infrastructure.exceptions;

public class ExistentCountryException extends RuntimeException{
    public ExistentCountryException (String message){
        super(message);
    }
}
