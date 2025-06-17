package be.pxl.researchproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MareNotFoundException extends RuntimeException{
        public MareNotFoundException(String message) {
            super(message);
        }
}
