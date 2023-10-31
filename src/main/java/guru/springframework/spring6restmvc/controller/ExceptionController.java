package guru.springframework.spring6restmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// @ControllerAdvice
public class ExceptionController {

    // @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpStatus> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
