package ru.otus.hw1611.app;


import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgWorker {
    void send(Msg msg);

    Msg pool();

    @Blocks
    Msg take() throws InterruptedException;

    void close() throws IOException;
}
