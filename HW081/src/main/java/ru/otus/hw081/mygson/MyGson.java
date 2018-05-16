package ru.otus.hw081.mygson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by rel on 10.05.2018.
 */
public class MyGson {
    public String toJson(Object obj) {
        return doWork(obj).toString();
    }
    private JSONObject doWork(Object obj) {
        Class<?> clazz = obj.getClass();
        JSONObject jsonObject = new JSONObject();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String className = field.get(obj).getClass().getName();
/*ARRAY CASE:*/ if(field.get(obj).getClass().isArray()) {
                    Object tmpObject = field.get(obj);
                    JSONArray array = new JSONArray();
                    if(checkArrayIsPrimitiveOrWrapper(className)) /*int[], Integer[]..*/{
                        for(int i = 0; i < Array.getLength(tmpObject); i++) {
                            array.add(Array.get(tmpObject, i));
                        }
                    } else /*user type[]:*/{
                        MyGson myGson = new MyGson();
                        for(int i = 0; i < Array.getLength(tmpObject); i++) {
                            JSONObject arrayElement = myGson.doWork(Array.get(tmpObject, i));
                            array.add(arrayElement);
                        }
                    }
                    jsonObject.put(field.getName(), array);
/*COLLECTIONS:*/} else if(checkIsACollection(className)) {
/*List or Set:*/    if(className.equals("java.util.ArrayList") || className.equals("java.util.LinkedList") || className.equals("java.util.Arrays$ArrayList") ||
                       className.equals("java.util.HashSet") || className.equals("java.util.LinkedHashSet") || className.equals("java.util.TreeSet")) {
                        Collection tmpCollection = (Collection) field.get(obj);
                        if(tmpCollection.isEmpty()) {
                            jsonObject.put(field.getName(), field.get(obj));
                        } else {
                            Iterator iter = tmpCollection.iterator();
                            Object element = iter.next();
                            if(checkIsAWrapper(element.getClass().getName())) {
                                jsonObject.put(field.getName(), field.get(obj));
                            } else {
                                JSONArray jsonArray = new JSONArray();
                                iter = tmpCollection.iterator(); //retake
                                while(iter.hasNext()) {
                                    jsonArray.add(doWork(iter.next()));
                                }
                                jsonObject.put(field.getName(), jsonArray);
                            }
                        }
/*Map:*/            }else if(className.equals("java.util.TreeMap") || className.equals("java.util.HashMap") || className.equals("java.util.LinkedHashMap")) {
                        Map tmpMap = (Map) field.get(obj);
                        if(tmpMap.isEmpty()) {
                            jsonObject.put(field.getName(), field.get(obj));
                        } else { //suppose - key is always a java wrapper (Integer, Double...)
                            Iterator iter = tmpMap.keySet().iterator();
                            Object element = tmpMap.get(iter.next());
                            if(checkIsAWrapper(element.getClass().getName())) {
                                jsonObject.put(field.getName(), field.get(obj));
                            } else {
                                JSONObject tmpObject = new JSONObject();
                                MyGson myGson = new MyGson();
                                iter = tmpMap.keySet().iterator();
                                while(iter.hasNext()) {
                                    Object key = iter.next();
                                    tmpObject.put(key, myGson.doWork(tmpMap.get(key)));
                                }
                                jsonObject.put(field.getName(), tmpObject);
                            }
                        }
                    }
/*PRIMITIVE OR WRAPPER OR USER CLASS:*/
                } else if(checkIsAWrapper(className) || field.get(obj).getClass().isPrimitive()) {
                    jsonObject.put(field.getName(), field.get(obj));
                } else {
                    MyGson myGson = new MyGson();
                    JSONObject composed = myGson.doWork(field.get(obj));
                    jsonObject.put(field.getName(), composed);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }
        return jsonObject;
    }

    public static boolean checkArrayIsPrimitiveOrWrapper(String className) {
        if(className.equals("[B") || className.equals("[S") || className.equals("[I") || className.equals("[J") ||
           className.equals("[C") || className.equals("[F") || className.equals("[D") || className.equals("[Z") ||
           className.equals("[Ljava.lang.Byte;") || className.equals("[Ljava.lang.Short;") ||
           className.equals("[Ljava.lang.Integer;") || className.equals("[Ljava.lang.Long;") ||
           className.equals("[Ljava.lang.Character;") || className.equals("[Ljava.lang.Float;") ||
           className.equals("[Ljava.lang.Double;") || className.equals("[Ljava.lang.Boolean;")
                || className.equals("[Ljava.lang.String;")) {
            return true;
        }
        return false;
    }

    public static boolean checkIsACollection(String className) {
        if(className.equals("java.util.ArrayList") || className.equals("java.util.LinkedList") ||
           className.equals("java.util.HashSet") || className.equals("java.util.LinkedHashSet") ||
           className.equals("java.util.TreeSet") || className.equals("java.util.TreeMap") ||
           className.equals("java.util.HashMap") || className.equals("java.util.LinkedHashMap") ||
           className.equals("java.util.Arrays$ArrayList")) {
            return true;
        }
        return false;
    }

    public static boolean checkIsAWrapper(String className) {
        if(className.equals("java.lang.Byte") || className.equals("java.lang.Short") ||
           className.equals("java.lang.Integer") || className.equals("java.lang.Long") ||
           className.equals("java.lang.Character") || className.equals("java.lang.Float") ||
           className.equals("java.lang.Double") || className.equals("java.lang.Boolean") || className.equals("java.lang.String")) {
            return true;
        }
        return false;
    }
}
