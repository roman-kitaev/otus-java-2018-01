package ru.otus.hw1611;

import ru.otus.hw1611.socketmessageserver.SocketMessageServer;

public class ServerMain {
    public static void main(String[] args) throws Exception{
        new SocketMessageServer().start();
    }
}
