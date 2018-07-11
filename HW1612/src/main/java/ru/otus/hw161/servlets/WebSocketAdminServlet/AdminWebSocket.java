package ru.otus.hw161.servlets.WebSocketAdminServlet;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw161.app.HitMiss;
import ru.otus.hw161.app.UserDataSet;

/**
 * @author v.chibrikov
 *         <p/>
 *         Пример кода для курса на https://stepic.org/
 *         <p/>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class AdminWebSocket {
    private AdminService adminService;
    private Session session;
    private Gson gson;

    public AdminWebSocket(AdminService adminService) {
        this.adminService = adminService;
        gson = new Gson();
        System.out.println("New web - socket connection");
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws Exception{
        adminService.add(this);
        this.session = session;

        HitMiss currentHitMiss = adminService.getCurrentHitMiss();
        String toSend = gson.toJson(currentHitMiss);

        sendString(toSend);
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws Exception{
        long id = Long.parseLong(data);

        UserDataSet userDataSet = adminService.getUserDataSetById(id);
        String toSend = gson.toJson(userDataSet);

        sendString(toSend);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        adminService.remove(this);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
