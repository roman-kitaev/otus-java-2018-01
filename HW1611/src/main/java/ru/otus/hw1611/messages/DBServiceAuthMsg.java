package ru.otus.hw1611.messages;

import ru.otus.hw1611.app.Msg;

public class DBServiceAuthMsg extends Msg {
    public DBServiceAuthMsg() {
        super(DBServiceAuthMsg.class);
    }

    @Override
    public String toString() {
        return "DBServiceAuthMsg{}";
    }
}
