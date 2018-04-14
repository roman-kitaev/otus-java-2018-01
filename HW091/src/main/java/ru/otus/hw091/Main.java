package ru.otus.hw091;

import ru.otus.hw091.base.MyDBService;
import ru.otus.hw091.base.UserDataSet;
import ru.otus.hw091.objectwriter.MyDBServiceObjectWriter;

/**
 * Created by rel on 07.04.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        try (MyDBService dbService = new MyDBServiceObjectWriter()) {
            dbService.prepareTables();
            dbService.addUsers("Vasiliy", "Ivan", "Petr");
            dbService.save(new UserDataSet(-1, "Ignat", 54));
            System.out.println("----->All names:");
            System.out.println(dbService.getAllNames());
            System.out.println("----->All users:");
            for(UserDataSet user : dbService.getAllUsers()) {
                System.out.println(user);
            }
            int id = 4;
            Class<?> clazz = UserDataSet.class;
            System.out.println("----->" + clazz.getSimpleName() + " with id = " + id + ":");
            System.out.println(dbService.load(id, UserDataSet.class));
        }
    }
}

