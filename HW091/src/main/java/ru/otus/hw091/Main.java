package ru.otus.hw091;

import ru.otus.hw091.base.ItemDataSet;
import ru.otus.hw091.base.MyDBService;
import ru.otus.hw091.base.UserDataSet;
import ru.otus.hw091.objectwriter.MyDBServiceObjectWriter;

import java.lang.reflect.Field;

/**
 * Created by rel on 07.04.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        try (MyDBService dbService = new MyDBServiceObjectWriter()) {
            UserDataSet set1 = new UserDataSet();
            set1.setName("Ivan"); set1.setAge(33); set1.setAddress("Russia"); set1.setNumberOfChildren(2);

            ItemDataSet set2 = new ItemDataSet();
            set2.setName("Computer"); set2.setPrice(30000); set2.setWeight(7);

            dbService.save(set1);
            dbService.save(set2);

            System.out.println(dbService.load(1, UserDataSet.class));
            System.out.println(dbService.load(1, ItemDataSet.class));
        }
    }
}

