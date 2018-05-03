package ru.otus.hw111;


import ru.otus.hw111.base.MyDBService;
import ru.otus.hw111.base.UserDataSet;
import ru.otus.hw111.objectwriter.MyDBServiceObjectWriter;

/**
 * Created by rel on 29.04.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        try (MyDBService dbService = new MyDBServiceObjectWriter()) {
            dbService.addScheme(UserDataSet.class);

            System.out.println("------------------------------------------------------");

            UserDataSet set1 = new UserDataSet();
            set1.setName("Ivan");
            set1.setAge(33);
            set1.setAddress("Russia");
            set1.setNumberOfChildren(2);

            UserDataSet set2 = new UserDataSet();
            set2.setName("Petr");
            set2.setAge(32);
            set2.setAddress("Armenia");
            set2.setNumberOfChildren(0);

            UserDataSet set3 = new UserDataSet();
            set3.setName("Ekaterina");
            set3.setAge(20);
            set3.setAddress("Russia");
            set3.setNumberOfChildren(0);

            dbService.save(set1);
            dbService.save(set2);
            dbService.save(set3);

            System.out.println("------------------------------------------------------");

            System.out.println(dbService.load(1, UserDataSet.class));
            System.out.println(dbService.load(2, UserDataSet.class));
            System.out.println(dbService.load(3, UserDataSet.class));
        }
    }
}
