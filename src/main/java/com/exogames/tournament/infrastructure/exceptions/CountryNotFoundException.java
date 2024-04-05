package com.exogames.tournament.infrastructure.exceptions;

public class CountryNotFoundException extends RuntimeException{
    public CountryNotFoundException (String message){
        super(message);
    }
}
