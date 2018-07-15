package ru.otus.hw1613;

import ru.otus.hw1611.socketmessageserver.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
public class DBServiceSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;

    DBServiceSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private DBServiceSocketMsgWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
