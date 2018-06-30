package ru.otus.hw151.messagesystem;

import ru.otus.hw151.base.Address;
import ru.otus.hw151.base.Addressee;
import ru.otus.hw151.base.Message;
import ru.otus.hw151.base.MessageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tully
 */
public final class MessageSystemImpl implements MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
    private static final int DEFAULT_STEP_TIME = 10;

    private final Map<Address, Thread> workers;
    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystemImpl() {
        workers = new HashMap<>();
        messagesMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());

        String name = "MS-worker-" + addressee.getAddress().getId();
        Thread thread = new Thread(() -> {
            while (true) {
                ConcurrentLinkedQueue<Message> queue = messagesMap.get(addressee.getAddress());
                while (!queue.isEmpty()) {
                    Message message = queue.poll();
                    message.exec(addressee);
                }
                try {
                    Thread.sleep(MessageSystemImpl.DEFAULT_STEP_TIME);
                } catch (InterruptedException e) {
                    logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                    return;
                }
                if (Thread.currentThread().isInterrupted()) {
                    logger.log(Level.INFO, "Finishing: " + name);
                    return;
                }
            }
        });
        thread.setName(name);
        thread.start();
        workers.put(addressee.getAddress(), thread);
    }

    public void removeAddressee(Addressee addressee) {
        workers.get(addressee.getAddress()).interrupt();
        workers.remove(addressee.getAddress());
        messagesMap.remove(addressee.getAddress());
        addresseeMap.remove(addressee.getAddress());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    public void dispose() {
        for (Map.Entry<Address, Thread> entry : workers.entrySet()) {
            entry.getValue().interrupt();
        }
    }
}
