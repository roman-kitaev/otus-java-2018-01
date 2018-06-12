package ru.otus.hw141;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by rel on 03.06.2018.
 */
public class Main {
    public static void main(String[] args) {
        int NN, KK;
        if(args[0] != null && args[1] != null) {
            NN = new Integer(args[0]);
            KK = new Integer(args[1]);
            if(NN % KK != 0) {
                System.out.println("Please use proper parameters!\nThe total " +
                        "amount of elements should be divided by N of parts " +
                        "without any remainder!");
                return;
            }
            System.out.println("Using parameters: \nNN = " + NN + "\nKK = " + KK);
        } else {
            NN = 6000000;
            KK = 4;
            System.out.println("Using default parameters: \nNN = " + NN + "\nKK = " + KK);
        }
        int partSize = NN / KK;

        int currentBegin = 0;
        Section[] sections = new Section[KK];
        for(int i = 0; i < KK; i++) {
            sections[i] = new Section(currentBegin, currentBegin + partSize);
            currentBegin += partSize;
        }
//////////////////////////////////////////////////
        System.out.println("Preparing new array...");
        int[] mass = new int[NN];
        Random rand = new Random(5);

        for(int i = 0; i < mass.length; i++) {
            mass[i] = rand.nextInt();
        }

        long timeBefore = System.currentTimeMillis();

        System.out.println("Sorting the array...");
        Thread[] threads = new Thread[KK];
        for(int i = 0; i < KK; i++) {
            threads[i] = new Thread(new SortIt(mass, sections[i]));
            threads[i].start();
        }

        try {
            for(int i = 0; i < KK; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {System.out.println("Joining in Main is interrupted!");}
//////////////////////////////////////////////////////////
        int[] result = new int[NN];
        Integer minElem;

        for(int j = 0; j < NN; j++) {
            minElem = null;
            for(int i = 0; i < KK; i++) {
                if(sections[i].getCurrentPos() != null) {
                    if(minElem != null) {
                        minElem = (minElem < mass[sections[i].getCurrentPos()] ? minElem : mass[sections[i].getCurrentPos()]);
                    } else {
                        minElem = mass[sections[i].getCurrentPos()];
                    }
                }
            }

            for(int i = 0; i < KK; i++) {
                if(sections[i].getCurrentPos() != null) {
                    if(mass[sections[i].getCurrentPos()] == minElem) {
                        sections[i].getAndIncrementCurrentPos();
                        break;
                    }
                }
            }
            result[j] = minElem;
        }

        long timeAfter = System.currentTimeMillis();

        if(CheckIt.checkTheArray(result)) {
            System.out.println("The array has been sorted properly for " + (timeAfter - timeBefore) + " milliseconds using " + KK + " threads!");
        } else {
            System.out.println("Please sort the array again!");
        }
///////////////////////////////////////////////
        mass = new int[NN];
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

