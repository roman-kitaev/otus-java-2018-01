package ru.otus.hw051;

import ru.otus.hw051.tester.MyTester;
/**
 * Created by rel on 07.03.2018.
 */
public class Main {
    private final static String PKG_PATH = Main.class.getPackage().getName().replace(".", "\\");
    public static void main(String[] args) {
        MyTester.startTestsByClassName("ru.otus.hw051.Tested");
        //MyTester.startTestByPackage("ru.otus.hw051", PKG_PATH);
        //MyTester.startTestByPackage("ru.otus.hw051.totest", PKG_PATH);
        //MyTester.startTestByPackage("ru.otus.hw051.totest.nested", PKG_PATH);
    }
}



