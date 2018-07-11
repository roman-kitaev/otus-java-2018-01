package ru.otus.hw161.messages;

import ru.otus.hw161.app.Msg;
import ru.otus.hw161.app.UserDataSet;

public class ReceiveUserMsg extends Msg {
    private final UserDataSet userDataSet;

    public ReceiveUserMsg(UserDataSet userDataSet) {
        super(ReceiveUserMsg.class);
        this.userDataSet = userDataSet;
    }

    public UserDataSet getUserDataSet() {
        return userDataSet;
    }

    @Override
    public String toString() {
        return "ReceiveUserMsg{" +
                "userDataSet=" + userDataSet +
                '}';
    }
}
