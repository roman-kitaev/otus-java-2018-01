package ru.otus.hw031;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MyHashSet<MyClass> mySet = new MyHashSet<>();
        System.out.println("Empty set:");
        System.out.println(mySet);
        System.out.println("Adding:");
        for(int i = 0; i < 8; i++) {
            mySet.add(new MyClass(i, true, "text" + i));
        }
        System.out.println(mySet);
        System.out.println("To array:");
        System.out.println(Arrays.toString(mySet.toArray()));
        System.out.print("Contains all:\n     List:\n     ");
        List<MyClass> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new MyClass(i, true, "text" + i));
        }
        System.out.println(list);
        System.out.println("     Contains: " + mySet.containsAll(list));
        list.add(new MyClass(11, false, "asd"));
        System.out.print("     List:\n     ");
        System.out.println(list);
        System.out.println("     Contains: " + mySet.containsAll(list));
        System.out.println("Add all: set adds list");
        mySet.addAll(list);
        System.out.println(mySet);
        System.out.println("Is empty: " + mySet.isEmpty());
        System.out.println("Size: " + mySet.size());
        System.out.println("Contains {11 false asd }: " + mySet.contains(new MyClass(11, false, "asd")));
        System.out.println("Contains {11 false asd_ }: " + mySet.contains(new MyClass(11, false, "asd_")));
        System.out.println("Remove {11 false asd }:");
        mySet.remove(new MyClass(11, false, "asd"));
        System.out.println(mySet);
        System.out.println("Output with iterator:");
        Iterator<MyClass> iter = mySet.iterator();
        while(iter.hasNext()) {
            System.out.print("-=" + iter.next() + "=-");
        }
        System.out.println();
        System.out.println("Clear:");
        mySet.clear();
        System.out.println(mySet);
        System.out.println("Is empty: " + mySet.isEmpty());
        System.out.println("Size: " + mySet.size());
    }
}
