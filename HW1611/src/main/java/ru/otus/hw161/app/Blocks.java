package ru.otus.hw161.app;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Blocks {
}
