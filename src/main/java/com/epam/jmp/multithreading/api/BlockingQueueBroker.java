package com.epam.jmp.multithreading.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueBroker<T> implements Broker<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockingQueueBroker.class);

    private BlockingQueue<T> blockingQueue;

    public BlockingQueueBroker() {
        this.blockingQueue = new ArrayBlockingQueue<T>(DEFAULT_CAPACITY);
    }

    public BlockingQueueBroker(BlockingQueue<T> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public T take() throws InterruptedException {
        T object = blockingQueue.take();
        LOGGER.info("Took " + object);
        return object;
    }

    @Override
    public void put(T object) throws InterruptedException {
        blockingQueue.put(object);
        LOGGER.info("Put " + object);
    }
}
