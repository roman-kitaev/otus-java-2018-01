package ru.otus.hw151.messagesystem;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.MessageSystem;

/**
 * Created by tully.
 */
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address socketServiceAddress;
    private Address dbAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getSocketServiceAddress() {
        return socketServiceAddress;
    }

    public void setSocketServiceAddress(Address socketServiceAddress) {
        this.socketServiceAddress = socketServiceAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
