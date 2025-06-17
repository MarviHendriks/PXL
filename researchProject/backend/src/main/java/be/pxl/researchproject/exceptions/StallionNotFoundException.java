package be.pxl.researchproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StallionNotFoundException extends RuntimeException {
    public StallionNotFoundException(String message){
        super(message);
    }
}
