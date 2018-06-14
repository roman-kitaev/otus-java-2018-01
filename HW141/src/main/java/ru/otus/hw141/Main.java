package ru.otus.hw141;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by rel on 03.06.2018.
 */
public class Main {
    private final static int RANDOM_SEED = 5;

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

        Long timeForThreads = sortViaThreads(arrayLength, threadsCount);
        if(timeForThreads != null) {
            System.out.println("The array has been sorted properly for " + timeForThreads + " milliseconds using " + threadsCount + " thread(s)!");
        } else {
            System.out.println("Please sort the array again!");
        }

        Long timeForLibrary = sortViaLibrary(arrayLength);
        if(timeForThreads != null) {
            System.out.println("The array has been sorted properly for " + timeForLibrary + " milliseconds using one thread!");
        } else {
            System.out.println("Please sort the array again!");
        }
    }

    private static Long sortViaThreads(int arrayLength, int threadsCount) {
        int[] mass = new int[arrayLength];
        Random rand = new Random(RANDOM_SEED);

        for(int i = 0; i < mass.length; i++) {
            mass[i] = rand.nextInt();
        }

        long timeBefore = System.currentTimeMillis();
        int[] result = SortIt.sort(mass, threadsCount);
        long timeAfter = System.currentTimeMillis();

        if(CheckIt.checkTheArray(result)) {
            return (timeAfter - timeBefore);
        } else {
            return null;
        }
    }

    private static Long sortViaLibrary(int arrayLength) {
        int[] mass = new int[arrayLength];
        Random rand = new Random(RANDOM_SEED);

        for(int i = 0; i < mass.length; i++) {
            mass[i] = rand.nextInt();
        }

        long timeBefore = System.currentTimeMillis();
        Arrays.sort(mass, 0, mass.length);
        long timeAfter = System.currentTimeMillis();

        if(CheckIt.checkTheArray(mass)) {
            return (timeAfter - timeBefore);
        } else {
            return null;
        }
    }
}

