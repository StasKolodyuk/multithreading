package com.epam.jmp.multithreading.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class IntProducer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntConsumer.class);

    private static final Integer PUT_TIME_MILLIS = 250;

    private Broker<Integer> broker;

    public IntProducer(Broker<Integer> broker) {
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            Integer number = new Random().nextInt(100);
            Thread.sleep(PUT_TIME_MILLIS);
            broker.put(number);
        } catch (InterruptedException e) {
            LOGGER.error("Thread was suddenly interrupted");
        }
    }
}
