package ru.otus.hw151.messages;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Addressee;
import ru.otus.hw151.base.Message;
import ru.otus.hw151.servlets.WebSocketAdminServlet.AdminService;

/**
 * Created by rel on 28.06.2018.
 */
public abstract class MsgToSocketService extends Message {
    public MsgToSocketService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if(addressee instanceof AdminService) {
            exec((AdminService) addressee);
        }
    }
    public abstract void exec(AdminService addressee);
}
