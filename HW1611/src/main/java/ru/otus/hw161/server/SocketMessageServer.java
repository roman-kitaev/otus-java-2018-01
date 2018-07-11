package ru.otus.hw161.server;

import ru.otus.hw161.app.Msg;
import ru.otus.hw161.app.MsgWorker;
import ru.otus.hw161.app.Blocks;
import ru.otus.hw161.messages.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageServer {
    private static final Logger logger = Logger.getLogger(SocketMessageServer.class.getName());
    private final ExecutorService executor;
    private final List<MsgWorker> beforeDistribution;
    private final List<MsgWorker> frontendWorkers;
    private final List<MsgWorker> dbWorkers;
    private long dbWorkersSwitcher = 0; //++ and %2 -> 0, 1, 0, 1, 0, 1 ...

    private static final int THREADS_NUMBER = 2;
    private static final int PORT = 5050;

    public SocketMessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        beforeDistribution = new CopyOnWriteArrayList<>();
        frontendWorkers = new CopyOnWriteArrayList<>();
        dbWorkers =  new CopyOnWriteArrayList<>();
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::distributor);
        executor.submit(this::listeningForRequests);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker client = new SocketMsgWorker(this, socket);
                client.init();
                beforeDistribution.add(client);
                logger.info("Server created a socket connection");
            }
        }
    }

    @Blocks
    private void distributor() {
        while (true) {
            for (MsgWorker client : beforeDistribution) {
                try {
                    Msg msg = client.take();
                    beforeDistribution.remove(client);
                    if(msg.getClass().equals(FrontendAuthMsg.class)) {
                        frontendWorkers.add(client);
                        logger.info("Server registered a frontend worker");
                    } else if(msg.getClass().equals(DBServiceAuthMsg.class)) {
                        dbWorkers.add(client);
                        logger.info("Server registered a db worker");
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }

    public void listeningForRequests() {
        try {
            while (true) {
                for (MsgWorker client : frontendWorkers) {
                    Msg msg = client.pool();
                    if (msg != null) {
                        if(msg.getClass().equals(GetUserMsg.class)) {
                            //logic for changing Data Base services
                            int currentDbWorkerNumber = (int)(dbWorkersSwitcher++) % 2;

                            MsgWorker currentDbWorker= dbWorkers.get(currentDbWorkerNumber);
                            currentDbWorker.send(msg);
                            Msg respond = currentDbWorker.take();

                            client.send(respond);
                        }
                        if(msg.getClass().equals(GetHitMissMsg.class)) {
                            //logic for changing Data Base services
                            int currentDbWorkerNumber = (int)(dbWorkersSwitcher++) % 2;

                            MsgWorker currentDbWorker= dbWorkers.get(currentDbWorkerNumber);
                            currentDbWorker.send(msg);
                            Msg respond = currentDbWorker.take();

                            client.send(respond);
                        }
                    }
                }
            }
        }catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public boolean removeMsgWorker(MsgWorker msgWorker) {
        logger.info("Server is unregistering a worker");
        return beforeDistribution.remove(msgWorker) ||
               frontendWorkers.remove(msgWorker) ||
               dbWorkers.remove(msgWorker);
    }
}