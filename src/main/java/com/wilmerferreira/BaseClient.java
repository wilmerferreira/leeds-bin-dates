package com.wilmerferreira;

public abstract class BaseClient {
    protected String subscriptionKey;

    public BaseClient(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }
}
