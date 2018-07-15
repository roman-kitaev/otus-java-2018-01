package ru.otus.hw1612;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw1612.servlets.AdminServlet;
import ru.otus.hw1612.servlets.HelloServlet;
import ru.otus.hw1612.servlets.LoginServlet;
import ru.otus.hw1612.servlets.WebSocketAdminServlet.WebSocketAdminServlet;
import ru.otus.hw1612.socket.FrontendRequestMaker;

/**
 * Created by rel on 06.05.2018.
 */
public class Main {
    private static int PORT = 8085;
    public static void main(String[] args) throws Exception {
        if(args.length == 1) {
            PORT = Integer.parseInt(args[0]);
        }
        System.out.println("The PORT is: " + PORT);
        System.out.println("PID CLIENT: " + System.getProperty( "process.id" ));
        //-------------------------------socket message system------------------
        FrontendRequestMaker frontendRequestMaker = new FrontendRequestMaker();
        //---------------------------------web----------------------------------
        ServletContextHandler servletContextHandlercontext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandlercontext.addServlet(
                new ServletHolder(new WebSocketAdminServlet(frontendRequestMaker)), "/wsadmin");
        servletContextHandlercontext.addServlet(new ServletHolder(new AdminServlet()), "/admin");
        servletContextHandlercontext.addServlet(new ServletHolder(new LoginServlet()), "/login");
        servletContextHandlercontext.addServlet(new ServletHolder(new HelloServlet()), "/");

        Server server = new Server(PORT);
        server.setHandler(servletContextHandlercontext);

        server.start();
    }
}
