package com.epam.jmp.multithreading.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntConsumer.class);

    private Broker<Integer> broker;

    public IntConsumer(Broker<Integer> broker) {
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            Integer number =  broker.take();
            // Some processing code goes here
            LOGGER.info("Processing " + number);
        } catch (InterruptedException e) {
            LOGGER.error("Thread was suddenly interrupted");
        }
    }
}
