package com.epam.jmp.multithreading.api;


public interface Broker<T> {

    Integer DEFAULT_CAPACITY = 10;

    T take() throws InterruptedException;
    void put(T object) throws InterruptedException;

}
