package ru.otus.hw161.messages;

import ru.otus.hw161.app.Msg;

public class GetHitMissMsg extends Msg {

    public GetHitMissMsg() {
        super(GetHitMissMsg.class);
    }

    @Override
    public String toString() {
        return "GetHitMissMsg{}";
    }
}
