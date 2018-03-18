package ru.otus.hw051.tester;

/**
 * Created by rel on 18.03.2018.
 */
public class BadTestException extends RuntimeException{
    public BadTestException(String message) {
        super(message);
    }
}
