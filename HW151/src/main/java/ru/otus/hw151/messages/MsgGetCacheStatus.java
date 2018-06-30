package ru.otus.hw151.messages;


import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.HitMiss;
import ru.otus.hw151.base.MyDBService;

/**
 * Created by rel on 27.06.2018.
 */
public class MsgGetCacheStatus extends MsgToDB {
    public MsgGetCacheStatus(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(MyDBService dbService) {
        HitMiss hitMiss = dbService.getCacheStatus();
        MsgGetCacheStatusAnswer msgGetCacheStatusAnswer = new MsgGetCacheStatusAnswer(getTo(), getFrom(), hitMiss);
        dbService.getMS().sendMessage(msgGetCacheStatusAnswer);
    }
}
