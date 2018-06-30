package ru.otus.hw151.servlets.WebSocketAdminServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.hw151.base.Address;
import ru.otus.hw151.messagesystem.MessageSystemContext;

import javax.servlet.annotation.WebServlet;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@WebServlet(name = "WebSocketAdminServlet", urlPatterns = {"/wsadmin"})
public class WebSocketAdminServlet extends WebSocketServlet {
    private int counter = 0;
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final AdminService adminService;
    private final MessageSystemContext messageSystemContext;

    public WebSocketAdminServlet(MessageSystemContext messageSystemContext) {
        this.messageSystemContext = messageSystemContext;
        this.adminService = new AdminService(messageSystemContext);
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new AdminWebSocket(new Address("WS" + counter++), messageSystemContext,
                 adminService));
    }

    public AdminService getAdminService() {
        return adminService;
    }
}
