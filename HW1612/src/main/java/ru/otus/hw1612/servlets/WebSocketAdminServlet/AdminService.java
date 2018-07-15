package ru.otus.hw1612.servlets.WebSocketAdminServlet;

import ru.otus.hw1611.app.HitMiss;
import ru.otus.hw1611.app.UserDataSet;
import ru.otus.hw1612.socket.FrontendRequestMaker;

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
public class AdminService {
    private Set<AdminWebSocket> webSockets;
    private final FrontendRequestMaker frontendRequestMaker;

    public AdminService(FrontendRequestMaker frontendRequestMaker) {
        this.frontendRequestMaker = frontendRequestMaker;
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
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

    public HitMiss getCurrentHitMiss() throws Exception{
        return frontendRequestMaker.getCurrentHitMiss();
    }

    public UserDataSet getUserDataSetById(long id) throws Exception{
        return frontendRequestMaker.getUserDataSetById(id);
    }

    public FrontendRequestMaker getFrontendRequestMaker() {
        return frontendRequestMaker;
    }
}
