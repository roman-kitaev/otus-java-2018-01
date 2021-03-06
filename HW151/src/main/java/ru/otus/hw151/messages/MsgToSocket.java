package ru.otus.hw151.messages;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Addressee;
import ru.otus.hw151.base.Message;
import ru.otus.hw151.servlets.WebSocketAdminServlet.AdminWebSocket;

/**
 * Created by rel on 27.06.2018.
 */
public abstract class MsgToSocket extends Message {
    public MsgToSocket(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if(addressee instanceof AdminWebSocket) {
            exec((AdminWebSocket) addressee);
        }
    }
    public abstract void exec(AdminWebSocket addressee);
}
