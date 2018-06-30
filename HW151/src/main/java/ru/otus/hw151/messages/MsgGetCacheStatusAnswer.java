package ru.otus.hw151.messages;

import com.google.gson.Gson;
import ru.otus.hw151.base.HitMiss;
import ru.otus.hw151.base.Address;
import ru.otus.hw151.servlets.WebSocketAdminServlet.AdminWebSocket;

/**
 * Created by rel on 27.06.2018.
 */
public class MsgGetCacheStatusAnswer extends MsgToSocket {
    HitMiss hitMiss;
    public MsgGetCacheStatusAnswer(Address from, Address to, HitMiss hitMiss) {
        super(from, to);
        this.hitMiss = hitMiss;
    }
    @Override
    public void exec(AdminWebSocket addressee) {
        Gson gson = new Gson();
        addressee.sendString(gson.toJson(hitMiss));
    }
}
