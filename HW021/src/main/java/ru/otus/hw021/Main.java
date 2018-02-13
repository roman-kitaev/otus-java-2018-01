package ru.otus.hw021;

import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * VM options -Xmx512m -Xms512m
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        long memBefore, memAfter;
        int size = 20_000_000;

        System.out.println("Starting the measurements: \n");

        memBefore = getMem();
        Object[] array = new Object[size];
        memAfter = getMem();
        System.out.println("Ref size: " + (memAfter - memBefore)/size);
        System.out.println("New array of size: " + array.length + " created\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Object();
        }
        memAfter = getMem();
        System.out.println("Object size: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new String(""); //String pool
        }
        memAfter = getMem();
        System.out.println("String with pool: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new String(new char[0]); //without String pool
        }
        memAfter = getMem();
        System.out.println("String without pool usage: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new MyClass();
        }
        memAfter = getMem();
        System.out.println("MyClass object size: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Object[0];
        }
        memAfter = getMem();
        System.out.println("Small array size: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new ArrayList<Object>(0);
        }
        memAfter = getMem();
        System.out.println("ArrayList without elements size: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new TreeSet<Object>();
        }
        memAfter = getMem();
        System.out.println("TreeSet without elements size: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        memBefore = getMem();
        array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new HashMap<Object, Object>();
        }
        memAfter = getMem();
        System.out.println("HashMap without elements size: " + (memAfter - memBefore)/size);
        System.out.println("Created " + size + " objects.\n");

        /*size = 2_000_000;
        Collection collection;
        memBefore = getMem();
        //collection = new ArrayList<>();
        collection = new HashSet<>();
        for(int i = 0; i < size; i++) {
            collection.add(new Object());
        }
        memAfter = getMem();
        System.out.println("Collection size: " + (memAfter - memBefore) + " bytes,");
        System.out.println("Added " + size + " objects.\n");*/

        Thread.sleep(1000); //wait for 1 sec


    }
    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static class MyClass {
        private int i = 0;
        private long l = 1;
    }
}
