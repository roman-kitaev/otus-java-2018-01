package ru.otus.hw081;

import com.google.gson.Gson;
import ru.otus.hw081.mygson.MyGson;
import ru.otus.hw081.testobjects.Composed;
import ru.otus.hw081.testobjects.TestObject;

import java.util.*;

/**
 * Created by rel on 09.05.2018.
 */
public class Main {
    public static void main(String[] args) {
        List<Composed> composedList = new ArrayList<>();
        composedList.add(new Composed(5, "5"));
        composedList.add(new Composed(6, "6"));

        Composed[] composeds = new Composed[2];
        composeds[0] = new Composed(2, "2");
        composeds[1] = new Composed(3, "3");

        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1, "a");
        map1.put(2, "b");
        Map<Integer, String> map2 = new HashMap<>();
        map2.put(1, "a");
        map2.put(2, "c");

        System.out.println(">>>>equals() checking:");
        TestObject object1 = new TestObject(3, new Integer(10), true, 4.3, 'a', "abc", new int[]{1, 2, 3}, new double[]{1.2, 2.2},
                new String[]{"abc", "cba"}, Arrays.asList(1, 2, 3), map1, new Composed(1, "ab"), composeds, composedList);
        TestObject object2 = new TestObject(3, new Integer(10), true, 4.3, 'a', "abc", new int[]{1, 2, 3}, new double[]{1.2, 2.2},
                new String[]{"abc", "cba"}, Arrays.asList(1, 2, 3), map1, new Composed(1, "ab"), composeds, composedList);
        TestObject object3 = new TestObject(3, new Integer(10), true, 4.3, 'a', "abc", new int[]{1, 2, 3}, new double[]{1.2, 2.2},
                new String[]{"abc", "cba"}, Arrays.asList(1, 2, 3), map1, new Composed(1, "ab"), new Composed[]{new Composed(4, "4")}, composedList);

        System.out.println(object1.equals(object2)); //true
        System.out.println(object1.equals(object3)); //false
        System.out.println("-------------------------------------");

        TestObject testObject = new TestObject(5, new Integer(10), false, 4.2, 'd', "ccc", new int[]{1, 2, 3}, new double[]{1.2, 2.2},
                new String[]{"abc", "cba"}, Arrays.asList(1, 2, 3), map1, new Composed(1, "ab"), composeds, composedList);

        Gson gson = new Gson();
        System.out.println(">>>>Gson:");
        System.out.println(gson.toJson(testObject));

        System.out.println("\n>>>>MyGson:");
        MyGson myGson = new MyGson();
        String myGsonString = myGson.toJson(testObject);
        System.out.println(myGsonString);

        System.out.println("-------------------------------------");

        TestObject restoredTestObject = gson.fromJson(myGsonString, TestObject.class);
        System.out.println(">>>>Restored object:");
        System.out.println(restoredTestObject);
        System.out.println();
        System.out.println(">>>>Restored object equals to initial object:");
        System.out.println(testObject.equals(restoredTestObject));
    }
}
