package com.epam.jmp.multithreading;

import com.epam.jmp.multithreading.api.BlockingQueueBroker;
import com.epam.jmp.multithreading.api.Broker;
import com.epam.jmp.multithreading.api.IntConsumer;
import com.epam.jmp.multithreading.api.IntProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.diffplug.common.base.Errors.rethrow;

public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    private static final Integer PRODUCER_CAPACITY = 10;
    private static final Integer CONSUMER_CAPACITY = 10;
    private static final Integer THREAD_COUNT = 100;
    private static final Integer TERMINATION_TIMEOUT_SEC = 5;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService producerExecutor = Executors.newFixedThreadPool(PRODUCER_CAPACITY);
        ExecutorService consumerExecutor = Executors.newFixedThreadPool(CONSUMER_CAPACITY);

        final Broker<Integer> broker = new BlockingQueueBroker<>();


        //Java 5+ Way
        /*for(int i = 0; i < THREAD_COUNT; i++) {
            producerExecutor.execute(new IntProducer(broker));
            consumerExecutor.execute(new IntConsumer(broker));
        }*/

        // Java 8 Way
        Supplier<Integer> supplier = () -> new Random().nextInt(100);
        Consumer<Integer> consumer = number -> LOGGER.info("Processing " + number);
        IntStream.range(0, THREAD_COUNT).forEach((i) -> producerExecutor.execute(rethrow().wrap(() -> broker.put(supplier.get()))));
        IntStream.range(0, THREAD_COUNT).forEach((i) -> consumerExecutor.execute(rethrow().wrap(() -> consumer.accept(broker.take()))));

        producerExecutor.shutdown();
        consumerExecutor.shutdown();

        producerExecutor.awaitTermination(TERMINATION_TIMEOUT_SEC, TimeUnit.SECONDS);
        consumerExecutor.awaitTermination(TERMINATION_TIMEOUT_SEC, TimeUnit.SECONDS);
    }

}
