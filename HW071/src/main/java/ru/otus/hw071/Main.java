package ru.otus.hw071;

import ru.otus.hw071.atm.ATM;
import ru.otus.hw071.atm.DynamicProgramming;
import ru.otus.hw071.atm.Greedy;
import ru.otus.hw071.observ.Observable;

/**
 * Created by rel on 27.03.2018.
 */
public class Main {
    public static void main(String[] args) {
        Observable department = new ATMDepartment();
        ATM atm1 = new ATM(new Greedy());
        ATM atm2 = new ATM(new Greedy());

        department.registerObserver(atm1);
        department.registerObserver(atm2);
        department.registerObserver(new ATM(new DynamicProgramming()));
        department.registerObserver(new ATM(new DynamicProgramming()));

        System.out.println("Current TOTAL balance = " + department.getBalances());
        System.out.println("------------------------------------");

        atm1.putSum(100);
        atm2.putSum(100);

        System.out.println("Current TOTAL balance = " + department.getBalances());
        System.out.println("------------------------------------");

        department.setInitialStates();

        System.out.println("Current TOTAL balance = " + department.getBalances());
    }
}
