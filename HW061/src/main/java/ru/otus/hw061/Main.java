package ru.otus.hw061;

import ru.otus.hw061.algorithm.DynamicProgramming;
import ru.otus.hw061.algorithm.Greedy;
import ru.otus.hw061.algorithm.Nominal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(new Greedy());

        atm.defaultLoad(5);

        System.out.println("Welcome to ATM!");
        System.out.print("I`m working with: ");
        for(Nominal nominal : Nominal.values()) {
            System.out.print(nominal.getValue() + " ");
        }
        System.out.println("russian banknotes!");
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
                    Map<Nominal, Integer> result = atm.withDraw(sum);
                    if(result != null) {
                        System.out.println("Please take it : ");
                        result.forEach(new BiConsumer<Nominal, Integer>() {
                            public void accept(Nominal nominal, Integer integer2) {
                                System.out.println(nominal + " x" + integer2);
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

