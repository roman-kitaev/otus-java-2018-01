package ru.otus.hw051;
import ru.otus.hw051.annotations.After;
import ru.otus.hw051.annotations.Before;
import ru.otus.hw051.annotations.Test;

/**
 * Created by rel on 08.03.2018.
 */
public class Tested {
    @Before
    public void a() {
        System.out.println("I`m a BEFORE from Tested!");
    }
    @Test
    public void b1() {
        System.out.println("I`m a TEST #1 from Tested");
    }
    @Test
    public void b2() {
        System.out.println("I`m a TEST #2 from Tested!");
    }
    @Test
    public void b3() {
        System.out.println("I`m a TEST #3 from Tested!");
    }
    @After
    public void c() {
        System.out.println("I`m an AFTER from Tested!");
    }
}
