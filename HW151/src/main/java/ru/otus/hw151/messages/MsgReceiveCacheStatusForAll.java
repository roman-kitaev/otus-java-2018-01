package ru.otus.hw151.messages;

import com.google.gson.Gson;
import ru.otus.hw151.base.HitMiss;
import ru.otus.hw151.base.Address;
import ru.otus.hw151.servlets.WebSocketAdminServlet.AdminService;

/**
 * Created by rel on 28.06.2018.
 */
public class MsgReceiveCacheStatusForAll extends MsgToSocketService {
    HitMiss hitMiss;
    public MsgReceiveCacheStatusForAll(Address from, Address to, HitMiss hitMiss) {
        super(from, to);
        this.hitMiss = hitMiss;
    }
    @Override
    public void exec(AdminService addressee) {
        Gson gson = new Gson();
        addressee.sendMessage(gson.toJson(hitMiss));
    }
}
