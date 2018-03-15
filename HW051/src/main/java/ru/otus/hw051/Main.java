package ru.otus.hw051;

import ru.otus.hw051.annotations.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by rel on 07.03.2018.
 */
public class Main {
    private static DirectoryStream.Filter<? super Path> filter = (Path entry) -> {
        return (Files.isDirectory(entry) || entry.toString().endsWith(".java"));
    };
    private final static String PKG_PATH = Main.class.getPackage().getName().replace(".", "\\");
    private static Set<String> classesSet;

    public static void main(String[] args) {
        //startTestsByClassName("ru.otus.hw051.Tested");
        startTestByPackage("ru.otus.hw051");
        //startTestByPackage("ru.otus.hw051.totest");
        //startTestByPackage("ru.otus.hw051.totest.nested");
    }
    static void startTestsByClassName(String className) {
        try {
            test(Class.forName(className));
        } catch (ClassNotFoundException e) {
            System.out.println("Can not find such class: " + className);
        }
    }
    static void startTestByPackage(String packageName) {
        classesSet = new HashSet<>();
        //to get all java files
        getAllClasses(Paths.get(""));
        Iterator<String> iter = classesSet.iterator();
        while(iter.hasNext()) {
            String currentJavaFile = iter.next();
            //to select files which belong to needed package
            if(currentJavaFile.startsWith(packageName)) {
                try {
                    Class<?> clazz = Class.forName(currentJavaFile);
                    test(clazz);
                } catch (ClassNotFoundException e) {
                    System.out.println("Can not find such class: " + currentJavaFile);
                }

            }
        }
    }
    public static void getAllClasses(Path path) {
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, filter)) {
            for(Path child : directoryStream) {
                if(Files.isDirectory(child)) { //dig the folder
                    getAllClasses(child);
                } else { //work with the file
                    String currentFileStr = child.toAbsolutePath().toString();
                    //example:
                    //if our package name is                              "ru\otus\hw051"    (from Main class)
                    //and our current path (Path child) is  "src\main\java\ru\otus\hw051\totest\Tested2.java"
                    //to get the package for Tested2 we need to emphasize "ru\otus\hw051\totest\Tested2"
                    //we will be able to give it to Class.forName()
                    //
                    //we find the first occurrence of "ru\otus\hw051" and cut the string since it
                    String currentPackageString = currentFileStr.substring(
                            currentFileStr.indexOf(PKG_PATH), currentFileStr.length());
                    //and delete ".java"
                    currentPackageString = currentPackageString.substring(0, currentPackageString.length() - 5);
                    //and make it look like a package name
                    currentPackageString = currentPackageString.replace('\\', '.');
                    classesSet.add(currentPackageString);
                    //we had: "src\main\java\ru\otus\hw051\totest\Tested2.java"
                    //now we have: "ru.otus.hw051.totest.Tested2"
                }
            }
        } catch (IOException e) {

        }
    }

    public static void test(Class<?> clazz) {
        Method before = null;
        Method after = null;
        List<Method> tests = new ArrayList<>();
        for(Method m : clazz.getDeclaredMethods()) {
            if(m.isAnnotationPresent(Test.class)) {
                tests.add(m);
            } else if(m.isAnnotationPresent(Before.class)) {
                before = m;
            } else if(m.isAnnotationPresent(After.class)) {
                after = m;
            }
        }
        if(before != null && after != null && tests.size() > 0) {
            System.out.println("Testing class < " + clazz.getName() + " >");
            try {
                for(Method m : tests) {
                    System.out.println();
                    System.out.println("Testing method < " + m.getName() + " >");
                    Object obj = clazz.newInstance();
                    before.invoke(obj);
                    m.invoke(obj);
                    after.invoke(obj);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {}
            System.out.println("--------------------------------------------------------");
        }
    }
}



