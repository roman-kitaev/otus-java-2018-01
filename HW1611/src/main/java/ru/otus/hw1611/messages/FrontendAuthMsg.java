package ru.otus.hw1611.messages;

import ru.otus.hw1611.app.Msg;

public class FrontendAuthMsg extends Msg {
    public FrontendAuthMsg() {
        super(FrontendAuthMsg.class);
    }

    @Override
    public String toString() {
        return "FrontendAuthMsg{}";
    }
}
