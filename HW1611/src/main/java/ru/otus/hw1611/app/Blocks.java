package ru.otus.hw1611.app;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Blocks {
}
