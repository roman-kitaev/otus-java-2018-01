package ru.otus.hw151.messages;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Message;
import ru.otus.hw151.base.MyDBService;
import ru.otus.hw151.base.UserDataSet;

import java.sql.SQLException;

/**
 * Created by rel on 28.06.2018.
 */
public class MsgGetUser extends MsgToDB {
    private long id;

    public MsgGetUser(Address from, Address to, long id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(MyDBService dbService) {
        UserDataSet userDataSet = null;
        try {
            userDataSet = dbService.load(id, UserDataSet.class);
        } catch (SQLException e) {
            //sending null
        }
        Message msg = new MsgReceiveUser(getTo(), getFrom(), userDataSet);
        dbService.getMS().sendMessage(msg);
    }
}
