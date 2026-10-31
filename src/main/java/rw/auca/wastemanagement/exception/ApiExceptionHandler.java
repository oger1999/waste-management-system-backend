package rw.auca.wastemanagement.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice

public class ApiExceptionHandler {
    @ExceptionHandler(
            ApiException.class
    )
public ResponseEntity<String> handle(ApiException e){
        return ResponseEntity
                .status(e
                        .getStatus())
                .body(e
                        .getMessage());
    }
}
