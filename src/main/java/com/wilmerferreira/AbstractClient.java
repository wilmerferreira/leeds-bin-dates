package com.wilmerferreira;

public abstract class AbstractClient {
    protected String subscriptionKey;

    public AbstractClient(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }
}
