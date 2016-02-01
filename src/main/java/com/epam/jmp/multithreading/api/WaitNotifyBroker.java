package com.epam.jmp.multithreading.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyBroker<T> implements Broker<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WaitNotifyBroker.class);

    private Queue<T> queue;

    public WaitNotifyBroker() {
        this.queue = new LinkedList<>();
    }

    @Override
    public synchronized T take() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();
        }
        T object = queue.remove();
        notifyAll();
        LOGGER.info("Took " + object);

        return object;
    }

    @Override
    public synchronized void put(T object) throws InterruptedException
    {
        while(queue.size() == DEFAULT_CAPACITY) {
            wait();
        }
        queue.add(object);
        notifyAll();
        LOGGER.info("Put " + object);
    }
}
