package ru.otus.hw161;

import ru.otus.hw161.runner.ProcessRunnerImpl;
import ru.otus.hw161.server.SocketMessageServer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain {
    private static final String WEBSERVER_START_COMMAND_1 =
            "java -jar C:/otus/HW1612/target/wsclient.jar 8087";
    private static final String WEBSERVER_START_COMMAND_2 =
            "java -jar C:/otus/HW1612/target/wsclient.jar 8088";
    private static final String DBSERVER_START_COMMAND =
            "java -jar C:/otus/HW1613/target/dbclient.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception{
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        startClient(executorService, WEBSERVER_START_COMMAND_1);
        startClient(executorService, WEBSERVER_START_COMMAND_2);
        startClient(executorService, DBSERVER_START_COMMAND);
        startClient(executorService, DBSERVER_START_COMMAND);
        ///////////////////////////////////////////////////////////////
        new SocketMessageServer().start();
    }

    private static void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}
