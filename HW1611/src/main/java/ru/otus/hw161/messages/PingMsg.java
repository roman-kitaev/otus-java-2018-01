package ru.otus.hw161.messages;


import ru.otus.hw161.app.Msg;

/**
 * Created by tully.
 */
public class PingMsg extends Msg {
    private final long time;

    public PingMsg() {
        super(PingMsg.class);
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PingMsg{" + "time=" + time + '}';
    }
}
