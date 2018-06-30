package ru.otus.hw151.base;

/**
 * Created by rel on 30.06.2018.
 */
public interface MessageSystem {
    public void addAddressee(Addressee addressee);
    public void removeAddressee(Addressee addressee);
    public void sendMessage(Message message);
    public void dispose();
}
