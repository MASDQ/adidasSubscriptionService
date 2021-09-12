package com.adidas.adidas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Class for generic response, both success or failure case
 * Result is the result of the operation, can be Success or Failure
 * Code is an code asociate to the error, 0 means no error
 * Message is the message to show to end user, it can be for example an Id for a successfully created Subscription
 *  or an exception message
 * TimeStamp is the moment which opperation occours
 */
@Getter
@Setter
@AllArgsConstructor
public class GenericResponse {
    String result;
    String code;
    Object message;
    Date timeStamp;

}
