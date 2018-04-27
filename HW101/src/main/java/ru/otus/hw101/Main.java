package ru.otus.hw101;

import ru.otus.hw101.base.DBService;
import ru.otus.hw101.base.dataSets.AddressDataSet;
import ru.otus.hw101.base.dataSets.PhoneDataSet;
import ru.otus.hw101.base.dataSets.UserDataSet;
import ru.otus.hw101.dbService.DBServiceHibernateImpl;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();

        UserDataSet user = new UserDataSet("Ivan", new AddressDataSet("SPB"));
        UserDataSet user2 = new UserDataSet("Peter", new AddressDataSet("MSK"));

        user.addPhone(new PhoneDataSet("000192345"));
        user.addPhone(new PhoneDataSet("000344555"));
        user.addPhone(new PhoneDataSet("000555555"));

        user2.addPhone(new PhoneDataSet("999333333"));
        user2.addPhone(new PhoneDataSet("999111111"));
        user2.addPhone(new PhoneDataSet("999222222"));

        System.out.println("___________________User to save: " + user);
        dbService.save(user);
        System.out.println("___________________User2 to save: " + user2);
        dbService.save(user2);

        UserDataSet userToRead;
        for(int id = 1; id < 3; id++) {
            userToRead = dbService.read(id);
            System.out.println("___________________User #" + id + " from the base: " + userToRead);
        }

        System.out.println("___________________All saved users:");
        for(UserDataSet userDataSet : dbService.getAllUsers()) {
            System.out.println("___________________" + userDataSet);
        }


        System.out.println("___________________All names: " + dbService.getAllNames());

        int id = 1;
        System.out.println("___________________Name by id = " + id + " is : " + dbService.getUserName(id));

        String[] names = {"Lida", "Nikolay", "Artem"};
        System.out.println("___________________Adding " + Arrays.toString(names));
        dbService.addUsers(names);

        System.out.println("___________________All saved users:");
        for(UserDataSet userDataSet : dbService.getAllUsers()) {
            System.out.println("___________________" + userDataSet);
        }

        System.out.println("___________________All names: " + dbService.getAllNames());
        dbService.shutdown();
    }
}
