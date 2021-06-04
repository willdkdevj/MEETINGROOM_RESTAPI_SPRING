package br.com.supernova.backroom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceRoomAlreadyRegisteredException extends Exception{

    public ResourceRoomAlreadyRegisteredException(String message){
        super(String.format("Room %s was already registered!", message));
    }
}
