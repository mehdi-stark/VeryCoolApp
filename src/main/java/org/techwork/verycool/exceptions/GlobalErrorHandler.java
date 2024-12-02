package org.techwork.verycool.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

    private ResponseEntity<ErrorMessage> handleException(Exception e) {
        return handleException(e, null);
    }

    private ResponseEntity<ErrorMessage> handleException(Exception e, HttpStatus status) {
        return ResponseEntity
                .status(status != null ? status : HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(TransformObjectException.class)
    public ResponseEntity<ErrorMessage> handleTransformObjectException(TransformObjectException e) {
        return handleException(e);
    }

    @ExceptionHandler(IdeaNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleIdeaNotFoundException(IdeaNotFoundException e) {
        return handleException(e);
   }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOtherException(Exception e) {
        log.error("An error occurred", e);
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
