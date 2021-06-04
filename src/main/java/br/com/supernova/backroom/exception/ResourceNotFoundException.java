package br.com.supernova.backroom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(Long id){
        super(String.format("The room with ID - %d was not found in the system!", id));
    }

    public ResourceNotFoundException(String name){
        super(String.format("Room with name %s was not found in the system!", name));
    }
}
