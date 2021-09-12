package com.adidas.adidas.common;

import com.adidas.adidas.models.GenericResponse;
import com.adidas.adidas.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
//TODO COMENTARIOS
public class CommonUtils {
    /**
     *
     * @param message
     * @return
     */
    public static GenericResponse successfullResponse(Object message){
        return new GenericResponse(Constants.Response.SUCCESSFULL_RESULT, Constants.Response.SUCCECSFULL_CODE, message, Calendar.getInstance().getTime());
    }

    public static  GenericResponse failureResponse(Object message){
        return new GenericResponse(Constants.Response.ERROR_RESULT, Constants.Response.ERROR_CODE, message, Calendar.getInstance().getTime());
    }

    public static Boolean validString(String valueToEvaluate){
        return valueToEvaluate!= null && !valueToEvaluate.isEmpty();
    }

    public static Boolean validList(ArrayList listToEvaluate){
        return listToEvaluate!= null && !listToEvaluate.isEmpty();
    }
    public static Boolean validObject(Object objectToEvaluate){
        return objectToEvaluate!= null; //TODO necesario
    }


}
