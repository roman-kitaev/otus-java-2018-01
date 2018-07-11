package ru.otus.hw161.socket;


import ru.otus.hw161.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
class FrontEndSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;

    FrontEndSocketMsgWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private FrontEndSocketMsgWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
