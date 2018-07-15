package ru.otus.hw1611.messages;

import ru.otus.hw1611.app.Msg;

public class GetHitMissMsg extends Msg {

    public GetHitMissMsg() {
        super(GetHitMissMsg.class);
    }

    @Override
    public String toString() {
        return "GetHitMissMsg{}";
    }
}
