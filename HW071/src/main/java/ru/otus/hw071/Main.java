package ru.otus.hw071;

import ru.otus.hw071.atm.AbstractAtm;
import ru.otus.hw071.atm.DPAlgorithmAtm;
import ru.otus.hw071.atm.GreedyAlgorithmAtm;
import ru.otus.hw071.observ.Observable;

/**
 * Created by rel on 27.03.2018.
 */
public class Main {
    public static void main(String[] args) {
        Observable department = new ATMDepartment();
        AbstractAtm atm1 = new DPAlgorithmAtm(department);
        AbstractAtm atm2 = new GreedyAlgorithmAtm(department);
        AbstractAtm atm3 = new DPAlgorithmAtm(department);
        new GreedyAlgorithmAtm(department);
        new DPAlgorithmAtm(department);
        new GreedyAlgorithmAtm(department);

        System.out.println("Current TOTAL balance = " + department.getBalances());
        System.out.println("------------------------------------");

        atm1.putSum(5000);
        atm2.putSum(5000);
        atm3.putSum(5000);

        System.out.println("Current TOTAL balance = " + department.getBalances());
        System.out.println("------------------------------------");

        department.setInitialStates();

        System.out.println("Current TOTAL balance = " + department.getBalances());
    }
}
