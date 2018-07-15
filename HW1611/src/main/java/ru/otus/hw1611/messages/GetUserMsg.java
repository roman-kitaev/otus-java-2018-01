package ru.otus.hw1611.messages;

import ru.otus.hw1611.app.Msg;

public class GetUserMsg extends Msg {
    private final long id;

    public GetUserMsg(long id) {
        super(GetUserMsg.class);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetUserMsg{" +
                "id=" + id +
                '}';
    }
}
