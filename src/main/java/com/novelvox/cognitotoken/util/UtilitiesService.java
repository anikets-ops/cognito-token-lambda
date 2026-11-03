package com.novelvox.cognitotoken.util;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


public class UtilitiesService {




    public static void logExceptionError(Exception e) {
        Object[] stackTrace =  Arrays.copyOf(e.getStackTrace(), 15);
        List<String> data = Stream.of(stackTrace)
                .filter(Objects::nonNull)
                .map(Object::toString).toList();
        System.out.println("Exception Message: "+e.getMessage()+"\n Exception StackTrace: "+ data);
    }
}
