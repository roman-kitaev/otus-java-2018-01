package ru.otus.hw041;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by rel on 28.02.2018.
 */
public class Main {
    private static int duration = 0, totalDuraton = 0;
    private static int young = 0, totalYoung = 0;
    private static int old = 0, totalOld = 0;
    public static void main(String[] args) throws Exception{
        final long GC_INFORMATION_PERIOD = 60 * 1000;

        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        installGCMonitoring();

        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
        scheduler.scheduleAtFixedRate(new PrintTask(), GC_INFORMATION_PERIOD, GC_INFORMATION_PERIOD, TimeUnit.MILLISECONDS);

        Thread t = new Thread(new WorkingTask());
        t.start();
    }
    private static void installGCMonitoring() {
        int i = 0;
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        System.out.println("Current collectors:");
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            System.out.println(gcbean.getName());

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();
                    String gctype = info.getGcAction();

                    if(gctype.equals("end of minor GC")) {
                        young++;
                    } else if(gctype.equals("end of major GC")) {
                        old++;
                    }
                    Main.duration += duration;
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
        System.out.println();
    }
    private static class PrintTask implements Runnable{
        @Override
        public void run() {
            System.out.println("-------------Duration: " + duration +
                    " ,Young: " + young + " ,Old: " + old);
            totalDuraton += duration; totalYoung += young; totalOld += old;
            duration = 0; young = 0; old = 0;
        }
    }
    private static class TerminatingTask {
        public static void showTerminatingInfo() {
            System.out.println("-------------Duration: " + duration +
                    " ,Young: " + young + " ,Old: " + old);
            totalDuraton += duration; totalYoung += young; totalOld += old;
            System.out.println("Total Duration: " + totalDuraton +
                    " ,Total Youngs: " + totalYoung + " ,Total Olds: " + totalOld);
        }
    }
    private static class WorkingTask implements Runnable {
        @Override
        public void run() {
            int size = 7 * 100 * 100;
            int i = 0;
            List<List<Integer>> list = new LinkedList<>();
            System.out.println("Starting the working task!");
            try {
                while(!Thread.interrupted()) {
                    list.add(new ArrayList<>());
                    for(int j = 0; j < size; j++) {
                        list.get(i).add(new Integer(i * j));
                    }
                    for(int j = 0; j < size / 2; j++) {
                        list.get(i).set(j, null);
                    }
                    try {
                        Thread.sleep(438); //438
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted from sleep :(");
                    }
                    i++;
                }
            } catch (OutOfMemoryError err) {
                TerminatingTask.showTerminatingInfo();
                System.out.println("OutOfMemoryError!!!");
                System.out.println("Finishing the Task!");

                System.exit(-1);
            }
        }
    }
}
