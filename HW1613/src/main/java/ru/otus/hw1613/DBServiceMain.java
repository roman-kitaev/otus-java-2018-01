package ru.otus.hw1613;

import ru.otus.hw1613.base.MyDBService;
import ru.otus.hw1613.cacheengine.SoftRefCacheEngine;
import ru.otus.hw1613.objectwriter.MyDBServiceObjectWriter;
import ru.otus.hw1611.app.DataSet;
import ru.otus.hw1611.app.HitMiss;
import ru.otus.hw1611.app.Msg;
import ru.otus.hw1611.app.UserDataSet;
import ru.otus.hw1611.messages.*;
import ru.otus.hw1611.socketmessageserver.SocketMsgWorker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBServiceMain {
    private static final Logger logger = Logger.getLogger(DBServiceMain.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("PID CLIENT: " + System.getProperty( "process.id" ));
        SoftRefCacheEngine<DataSet> cacheEngine = new SoftRefCacheEngine<>(6);
        //----------------------------------DB-------------------------------------------------
        MyDBService dbService = new MyDBServiceObjectWriter(cacheEngine);
        dbService.addScheme(UserDataSet.class);

        System.out.println("------------------------------------------------------");

        UserDataSet set1 = new UserDataSet();
        set1.setName("Ivan");
        set1.setAge(33);
        set1.setAddress("Russia");
        set1.setNumberOfChildren(2);

        UserDataSet set2 = new UserDataSet();
        set2.setName("Petr");
        set2.setAge(32);
        set2.setAddress("Armenia");
        set2.setNumberOfChildren(0);

        UserDataSet set3 = new UserDataSet();
        set3.setName("Ekaterina");
        set3.setAge(20);
        set3.setAddress("Russia");
        set3.setNumberOfChildren(0);

        dbService.save(set1);
        dbService.save(set2);
        dbService.save(set3);

        System.out.println("------------------------------------------------------");

        System.out.println(dbService.load(1, UserDataSet.class));
        System.out.println(dbService.load(2, UserDataSet.class));
        System.out.println(dbService.load(3, UserDataSet.class));
        //------------------------------Socket------------------------
        System.out.println("------------------------------------------------------");
        SocketMsgWorker client = new DBServiceSocketMsgWorker(HOST, PORT);
        client.init();
        client.send(new DBServiceAuthMsg());

        try {
            while(true) {
                Msg msg = client.take();
                if(msg.getClass().equals(GetUserMsg.class)) {
                    long id = ((GetUserMsg)msg).getId();
                    UserDataSet userDataSet;

                    try {
                        userDataSet = dbService.load(id, UserDataSet.class);
                    } catch (SQLException e) {
                        userDataSet = new UserDataSet();
                        userDataSet.setType("nulluser");
                    }

                    client.send(new ReceiveUserMsg(userDataSet));
                }
                else if(msg.getClass().equals(GetHitMissMsg.class)) {
                    HitMiss hitMiss = dbService.getCacheStatus();
                    client.send(new ReceiveHitMissMsg(hitMiss));
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
