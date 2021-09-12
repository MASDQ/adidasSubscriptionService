package com.adidas.adidas.utils;

import lombok.Getter;

/**
 * Class for all the constands used on the application
 */
public interface Constants {

    /**
     * Constants asociated to response, both success or failure
     */
    @Getter
    class Response {
        public static final String SUCCECSFULL_CODE = "OK";
        public static final String SUCCESSFULL_RESULT = "Success";
        public static final String ERROR_CODE = "KO";
        public static final String ERROR_RESULT = "Error";
        public static final String RETURN_SUBSCRIPTION_CREATION_OK = "Subcription ID: ";
        public static final String RETURN_SUBSCRIPTION_CREATION_KO = "Subscription cannot be created";
        public static final String RETURN_SUBSCRIPTION_DELETION_PREVIOUS="Subscription ";
        public static final String RETURN_SUBSCRIPTION_DELETION_SUCCESS=" deleted successfully";
        public static final String RETURN_SUBSCRIPTION_DELETION_FAILURE=" cannot be delete";
        public static final String RETURN_SUBSCRIPTION_SEARCH_FAILURE="Subscription doesn't exist";
        public static final String RETURN_SUBSCRIPTION_SEARCH_MANY_FAILURE="Any subscription exist";
    }

    String MAIL_URI = "http://localhost:8081/email/send/";

}
