package com.adidas.adidas.exception;

import com.adidas.adidas.common.CommonUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class to control exceptions
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger logger = LogManager.getLogger(ExceptionHandlingController.class);

    /**
     * Manage of unexpecte exceptions
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleError(Exception ex) {
        logger.error("SubscriptionController - Exception Handler: Unexpected exception receibed");
        logger.error("Message: " + ex.getMessage());
        logger.error("Cause: " + ex.getCause());
        return new ResponseEntity<>(CommonUtils.failureResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manage of exceptions due an error on request petition or precessing
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = FailedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleError(FailedOperationException ex) {
        logger.error("SubscriptionController - Exception Handler: an operation generate an exception");
        logger.error("Message: " + ex.getMessage());
        logger.error("Cause: " + ex.getCause());
        return new ResponseEntity<>(CommonUtils.failureResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}