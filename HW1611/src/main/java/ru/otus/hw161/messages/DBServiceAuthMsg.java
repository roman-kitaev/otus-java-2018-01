package ru.otus.hw161.messages;

import ru.otus.hw161.app.Msg;

public class DBServiceAuthMsg extends Msg {
    public DBServiceAuthMsg() {
        super(DBServiceAuthMsg.class);
    }

    @Override
    public String toString() {
        return "DBServiceAuthMsg{}";
    }
}
