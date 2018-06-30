package ru.otus.hw151.servlets.WebSocketAdminServlet;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Addressee;
import ru.otus.hw151.base.MessageSystem;
import ru.otus.hw151.messagesystem.MessageSystemContext;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AdminService implements Addressee{
    private Set<AdminWebSocket> webSockets;

    private final Address address;
    private final MessageSystemContext context;

    public AdminService(MessageSystemContext messageSystemContext) {
        this.context = messageSystemContext;
        this.address = messageSystemContext.getSocketServiceAddress();
        context.getMessageSystem().addAddressee(this);
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    public void sendMessage(String data) {
        for (AdminWebSocket admin : webSockets) {
            try {
                admin.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void add(AdminWebSocket webSocket) {
        webSockets.add(webSocket);

    }

    public void remove(AdminWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

}
