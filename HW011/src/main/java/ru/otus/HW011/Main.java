package ru.otus.HW011;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by rel on 03.02.2018.
 */
public class Main {
    public static void main(String[] args) {
        List<String> myList = Lists.newArrayList("8", "2", "7", "10");
        String result = Joiner.on(",").join(myList);
        System.out.println(result);
    }
}
