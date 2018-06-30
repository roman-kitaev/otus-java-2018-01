package ru.otus.hw151.messages;

import com.google.gson.Gson;
import ru.otus.hw151.base.UserDataSet;
import ru.otus.hw151.base.Address;
import ru.otus.hw151.servlets.WebSocketAdminServlet.AdminWebSocket;


/**
 * Created by rel on 28.06.2018.
 */
public class MsgReceiveUser extends MsgToSocket {
    UserDataSet user;
    public MsgReceiveUser(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }
    @Override
    public void exec(AdminWebSocket addressee) {
        if(user == null) {
            user = new UserDataSet();
            user.setType("nulluser");
        }
        Gson gson = new Gson();
        addressee.sendString(gson.toJson(user));    }
}
