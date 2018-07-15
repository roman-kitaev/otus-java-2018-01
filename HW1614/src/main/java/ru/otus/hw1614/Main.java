package ru.otus.hw1614;

import ru.otus.hw1614.runner.ProcessRunnerImpl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String SERVER_START_COMMAND =
            "java -jar C:/otus/HW1611/target/server.jar";
    private static final String WEBSERVER_START_COMMAND_1 =
            "java -jar C:/otus/HW1612/target/wsclient.jar 8087";
    private static final String WEBSERVER_START_COMMAND_2 =
            "java -jar C:/otus/HW1612/target/wsclient.jar 8088";
    private static final String DBSERVER_START_COMMAND =
            "java -jar C:/otus/HW1613/target/dbclient.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        startApp(executorService, SERVER_START_COMMAND, 1);
        startApp(executorService, WEBSERVER_START_COMMAND_1);
        startApp(executorService, WEBSERVER_START_COMMAND_2);
        startApp(executorService, DBSERVER_START_COMMAND);
        startApp(executorService, DBSERVER_START_COMMAND);
    }

    private static void startApp(ScheduledExecutorService executorService, String command) {
        startApp(executorService, command, CLIENT_START_DELAY_SEC);
    }

    private static void startApp(ScheduledExecutorService executorService, String command, int delay) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
            }
        }, delay, TimeUnit.SECONDS);
    }
}
