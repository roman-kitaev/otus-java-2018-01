package ru.otus.hw061;

import ru.otus.hw061.algorithm.DynamicProgramming;
import ru.otus.hw061.algorithm.Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(new Greedy());

        atm.defaultLoad(3);

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
                if(str.equals("-b")) System.out.println("Current balance is : " + atm.getBalance());
                splitted = str.split(" ");
                if(splitted[0].equals("-put")) {
                    Integer banknote = Integer.parseInt(splitted[1]);
                    if(atm.putSum(banknote) ==  true) {
                        System.out.println(banknote + " was accepted");
                    } else {
                        System.out.println("I don`t work with this bank note!");
                    }
                }
                if(splitted[0].equals("-wd")) {
                    Integer sum = Integer.parseInt(splitted[1]);
                    System.out.println("I`m trying to withdraw : " + sum + " RUB");
                    Map<Integer, Integer> result = atm.withDraw(sum);
                    if(result != null) {
                        System.out.println("Please take it : ");
                        result.forEach(new BiConsumer<Integer, Integer>() {
                            public void accept(Integer integer, Integer integer2) {
                                System.out.println(integer + " x" + integer2);
                            }
                        });
                    } else {
                        System.out.println("I don`t have enough coins for this sum.");
                    }
                }
            }


        } catch (IOException e) {
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please be more careful next time and follow the instructions!");
        }
        System.out.println("Good bye!");
    }
}

