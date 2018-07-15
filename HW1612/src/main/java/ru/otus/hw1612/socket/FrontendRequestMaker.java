package ru.otus.hw1612.socket;



import ru.otus.hw1611.app.HitMiss;
import ru.otus.hw1611.app.UserDataSet;
import ru.otus.hw1611.messages.*;
import ru.otus.hw1611.socketmessageserver.SocketMsgWorker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by tully.
 */
public class FrontendRequestMaker {
    private static final Logger logger = Logger.getLogger(FrontendRequestMaker.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    private SocketMsgWorker client;

    public FrontendRequestMaker() throws IOException {
        client = new FrontEndSocketMsgWorker(HOST, PORT);
        client.init();
        client.send(new FrontendAuthMsg());
    }

    public HitMiss getCurrentHitMiss(){
        client.send(new GetHitMissMsg());
        ReceiveHitMissMsg receiveHitMissMsg = null;

        try {
            receiveHitMissMsg =
                    (ReceiveHitMissMsg) client.take();
            //System.out.println("A Message received: " + receiveHitMissMsg.getHitMiss());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.toString());
        }

        return receiveHitMissMsg.getHitMiss();
    }

    public UserDataSet getUserDataSetById(long id) {
        client.send(new GetUserMsg(id));
        ReceiveUserMsg receiveUserMsg = null;

        try {
            receiveUserMsg = (ReceiveUserMsg) client.take();
            //System.out.println("A Message received: " + receiveUserMsg.getUserDataSet());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.toString());
        }

        return receiveUserMsg.getUserDataSet();
    }
}
