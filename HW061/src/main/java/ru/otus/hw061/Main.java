package ru.otus.hw061;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        //AbstractAtm atm = new DPAlgorithmAtm();
        AbstractAtm atm = new GreedyAlgorithmAtm();

        atm.defaultLoad();

        System.out.println("Welcome to ATM!");
        System.out.println("I`m working with: 1 2 5 10 50 100 200 500 1000 2000 5000 russian banknotes!");
        System.out.println("Please type :\n" +
        "-put &note    to put a banknote to me\n" +
        "-wd &sum      to withdraw a definite sum\n" +
        "-b            to get your balance\n" +
        "-q            to stop working\n" +
        "----------------------------------------------------");
        try {
            String str;
            String[] splitted;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                str = reader.readLine();
                if(str.equals("-q")) break;
                if(str.equals("-b")) atm.getBalance();
                splitted = str.split(" ");
                if(splitted[0].equals("-put")) {
                    Integer banknote = Integer.parseInt(splitted[1]);
                    atm.putSum(banknote);
                }
                if(splitted[0].equals("-wd")) {
                    Integer sum = Integer.parseInt(splitted[1]);
                    atm.withDraw(sum);
                }
            }


        } catch (IOException e) {
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please be more careful next time and follow the instructions!");
        }
        System.out.println("Good bye!");
    }
}

