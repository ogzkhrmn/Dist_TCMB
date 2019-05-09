package com.bank.tcmb.core.util;

import com.bank.tcmb.core.model.AnnotatedFieldModel;

import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    private static Map<String, Class> serviceBeans = new HashMap<>();
    private static Map<String, AnnotatedFieldModel> serviceFields = new HashMap<>();

    public static void addBean(String serviceName, Class clazz){
        serviceBeans.put(serviceName, clazz);
    }

    public static Class getBean(String serviceName){
        return serviceBeans.get(serviceName);
    }

    public static void addField(String serviceName, AnnotatedFieldModel field){
        serviceFields.put(serviceName, field);
    }

    public static AnnotatedFieldModel getField(String serviceName){
        return serviceFields.get(serviceName);
    }

    public static Map<String, AnnotatedFieldModel> getFields(){
        return serviceFields;
    }
}
