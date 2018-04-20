package ru.otus.hw091;

import ru.otus.hw091.base.ItemDataSet;
import ru.otus.hw091.base.MyDBService;
import ru.otus.hw091.base.UserDataSet;
import ru.otus.hw091.objectwriter.MyDBServiceObjectWriter;

/**
 * Created by rel on 07.04.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        try (MyDBService dbService = new MyDBServiceObjectWriter()) {
            //dbService.addSchemes(ItemDataSet.class, UserDataSet.class); //does not work:(
            dbService.addScheme(ItemDataSet.class);
            dbService.addScheme(UserDataSet.class);

            UserDataSet set1 = new UserDataSet();
            set1.setName("Ivan");
            set1.setAge(33);
            set1.setAddress("Russia");
            set1.setNumberOfChildren(2);

            ItemDataSet set2 = new ItemDataSet();
            set2.setName("Computer");
            set2.setPrice(30000);
            set2.setWeight(7);

            ItemDataSet set3 = new ItemDataSet();
            set3.setName("PlayStation");
            set3.setPrice(28000);
            set3.setWeight(4);

            dbService.save(set1);
            dbService.save(set2);
            dbService.save(set3);

            System.out.println(dbService.load(1, UserDataSet.class));
            System.out.println(dbService.load(1, ItemDataSet.class));
            System.out.println(dbService.load(2, ItemDataSet.class));
        }
    }
}

