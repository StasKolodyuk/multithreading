package com.epam.jmp.multithreading.api;


public interface Broker<T> {

    T take() throws InterruptedException;
    void put(T object) throws InterruptedException;

}
