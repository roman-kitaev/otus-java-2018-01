package ru.otus.hw151;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw151.base.*;
import ru.otus.hw151.cacheengine.SoftRefCacheEngine;
import ru.otus.hw151.messagesystem.MessageSystemContext;
import ru.otus.hw151.messagesystem.MessageSystemImpl;
import ru.otus.hw151.objectwriter.MyDBServiceObjectWriter;
import ru.otus.hw151.servlets.AdminServlet;
import ru.otus.hw151.servlets.HelloServlet;
import ru.otus.hw151.servlets.LoginServlet;
import ru.otus.hw151.servlets.WebSocketAdminServlet.WebSocketAdminServlet;

/**
 * Created by rel on 06.05.2018.
 */
public class Main {
    private final static int PORT = 8080;
    private final static String PUBLIC_HTML = "public_html";
    public static void main(String[] args) throws Exception {
        SoftRefCacheEngine<DataSet> cacheEngine = new SoftRefCacheEngine<>(6);
        //----------------------------MessageSystem--------------------------------------------
        MessageSystem messageSystem = new MessageSystemImpl();
        MessageSystemContext messageSystemContext = new MessageSystemContext(messageSystem);
        Address dbAddress = new Address("DB");
        messageSystemContext.setDbAddress(dbAddress);
        Address wsServiceAddress = new Address("WSS");
        messageSystemContext.setSocketServiceAddress(wsServiceAddress);
        //---------------------------------WEB---------------------------------------------------
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler servletContextHandlercontext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandlercontext.addServlet(
                new ServletHolder(new WebSocketAdminServlet(messageSystemContext)), "/wsadmin");
        servletContextHandlercontext.addServlet(new ServletHolder(new AdminServlet(cacheEngine)), "/admin");
        servletContextHandlercontext.addServlet(new ServletHolder(new LoginServlet()), "/login");
        servletContextHandlercontext.addServlet(new ServletHolder(new HelloServlet()), "/");

        Server server = new Server(PORT);
        server.setHandler(servletContextHandlercontext);

        server.start();
        //----------------------------------DB-------------------------------------------------
        MyDBService dbService = new MyDBServiceObjectWriter(messageSystemContext, dbAddress, cacheEngine);
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

        //server.join();
    }
}
