package ru.otus.hw121;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw121.base.DataSet;
import ru.otus.hw121.base.MyDBService;
import ru.otus.hw121.base.UserDataSet;
import ru.otus.hw121.cacheengine.SoftRefCacheEngine;
import ru.otus.hw121.objectwriter.MyDBServiceObjectWriter;
import ru.otus.hw121.servlets.AdminServlet;
import ru.otus.hw121.servlets.LoginServlet;

/**
 * Created by rel on 06.05.2018.
 */
public class Main {
    private final static int PORT = 8070;
    private final static String PUBLIC_HTML = "public_html";
    public static void main(String[] args) throws Exception {
        SoftRefCacheEngine<DataSet> cacheEngine = new SoftRefCacheEngine<>(2);
        //---------------------------------WEB---------------------------------------------------
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AdminServlet(cacheEngine)), "/admin");
        context.addServlet(new ServletHolder(new LoginServlet()), "/");

        Server server = new Server(PORT);
        server.setHandler(context);

        server.start();
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

        //server.join();
    }
}
