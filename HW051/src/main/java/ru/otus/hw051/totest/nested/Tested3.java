package ru.otus.hw051.totest.nested;

import ru.otus.hw051.annotations.After;
import ru.otus.hw051.annotations.Before;
import ru.otus.hw051.annotations.Test;

/**
 * Created by rel on 14.03.2018.
 */
public class Tested3 {
    @Before
    public void aaa() {
        System.out.println("I`m a BEFORE from Tested3!");
    }
    @Test
    public void bbb1() {
        System.out.println("I`m a TEST #1 from Tested3!");
    }
    @Test
    public void bbb2() {
        System.out.println("I`m a TEST #2 from Tested3!");
    }
    @Test
    public void bbb3() {
        System.out.println("I`m a TEST #3 from Tested3!");
    }
    @After
    public void ccc() {
        System.out.println("I`m an AFTER from Tested3!");
    }
}
