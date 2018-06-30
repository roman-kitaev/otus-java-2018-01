package ru.otus.hw151.messages;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Addressee;
import ru.otus.hw151.base.Message;
import ru.otus.hw151.base.MyDBService;

/**
 * Created by rel on 27.06.2018.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof MyDBService) {
            exec((MyDBService) addressee);
        }
    }

    public abstract void exec(MyDBService dbService);
}
