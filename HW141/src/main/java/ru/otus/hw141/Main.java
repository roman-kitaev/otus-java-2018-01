package ru.otus.hw141;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by rel on 03.06.2018.
 */
public class Main {
    public static void main(String[] args) {
        int arrayLength, threadsCount;
        if(args.length == 2) {
            arrayLength = new Integer(args[0]);
            threadsCount = new Integer(args[1]);
            if(arrayLength % threadsCount != 0) {
                System.out.println("Please use proper parameters!\nThe total " +
                        "amount of elements should be divided by N of parts " +
                        "without any remainder!");
                return;
            }
            System.out.println("Using parameters: \narrayLength = " + arrayLength + "\nthreadsCount = " + threadsCount);
        } else {
            arrayLength = 6000000;
            threadsCount = 4;
            System.out.println("Using default parameters: \narrayLength = " + arrayLength + "\nthreadsCount = " + threadsCount);
        }

        /////////////////////////////////////////////////////////////////////

        System.out.println("Preparing new array...");
        int[] mass = new int[arrayLength];
        Random rand = new Random(5);

        for(int i = 0; i < mass.length; i++) {
            mass[i] = rand.nextInt();
        }

        long timeBefore = System.currentTimeMillis();
        System.out.println("Sorting the array...");
        int[] result = SortIt.sort(mass, threadsCount);
        long timeAfter = System.currentTimeMillis();

        if(CheckIt.checkTheArray(result)) {
            System.out.println("The array has been sorted properly for " + (timeAfter - timeBefore) + " milliseconds using " + threadsCount + " threads!");
        } else {
            System.out.println("Please sort the array again!");
        }

        /////////////////////////////////////////////////////////////////////

        mass = new int[arrayLength];
        rand = new Random(5);

        for(int i = 0; i < mass.length; i++) {
            mass[i] = rand.nextInt();
        }

        timeBefore = System.currentTimeMillis();
        Arrays.sort(mass, 0, mass.length);
        timeAfter = System.currentTimeMillis();

        if(CheckIt.checkTheArray(mass)) {
            System.out.println("The array has been sorted properly for " + (timeAfter - timeBefore) + " milliseconds using one thread!");
        } else {
            System.out.println("Please sort the array again!");
        }
    }
}

