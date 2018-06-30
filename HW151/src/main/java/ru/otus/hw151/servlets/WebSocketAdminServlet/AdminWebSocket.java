package ru.otus.hw151.servlets.WebSocketAdminServlet;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Addressee;
import ru.otus.hw151.base.MessageSystem;
import ru.otus.hw151.messages.MsgGetCacheStatus;
import ru.otus.hw151.messages.MsgGetUser;
import ru.otus.hw151.messagesystem.*;

/**
 * @author v.chibrikov
 *         <p/>
 *         Пример кода для курса на https://stepic.org/
 *         <p/>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class AdminWebSocket implements Addressee {
    private AdminService adminService;
    private Session session;
    private final Address address;
    private final MessageSystemContext context;

    public AdminWebSocket(Address address, MessageSystemContext context, AdminService adminService) {
        this.address = address;
        this.context = context;
        this.adminService = adminService;
        System.out.println("New socket connection!!");
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        adminService.add(this);
        this.session = session;
        context.getMessageSystem().addAddressee(this);
        context.getMessageSystem().sendMessage(new MsgGetCacheStatus(
                this.address,
                context.getDbAddress()));
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        context.getMessageSystem().sendMessage(new MsgGetUser(getAddress(),
                context.getDbAddress(), Integer.parseInt(data)));
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        adminService.remove(this);
        context.getMessageSystem().removeAddressee(this);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
