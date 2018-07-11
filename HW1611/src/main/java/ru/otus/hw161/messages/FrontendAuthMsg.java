package ru.otus.hw161.messages;

import ru.otus.hw161.app.Msg;

public class FrontendAuthMsg extends Msg {
    public FrontendAuthMsg() {
        super(FrontendAuthMsg.class);
    }

    @Override
    public String toString() {
        return "FrontendAuthMsg{}";
    }
}
