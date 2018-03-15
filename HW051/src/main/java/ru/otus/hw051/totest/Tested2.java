package ru.otus.hw051.totest;

import ru.otus.hw051.annotations.After;
import ru.otus.hw051.annotations.Before;
import ru.otus.hw051.annotations.Test;

/**
 * Created by rel on 13.03.2018.
 */
public class Tested2 {
    @Before
    public void aa() {
        System.out.println("I`m a BEFORE from Tested2!");
    }
    @Test
    public void bb1() {
        System.out.println("I`m a TEST #1 from Tested2!");
    }
    @Test
    public void bb2() {
        System.out.println("I`m a TEST #2 from Tested2!");
    }
    @Test
    public void bb3() {
        System.out.println("I`m a TEST #3 from Tested2!");
    }
    @After
    public void cc() {
        System.out.println("I`m an AFTER from Tested2!");
    }
}
