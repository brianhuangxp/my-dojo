package com.ringcentral.domain;

public enum GameStatus {
    INITIAL,
    STARTING,
    SEND_CARD,
    COMPLETED;

    public boolean isStarting() {
        return this == STARTING;
    }

    public boolean isCompeted() {
        return  this == COMPLETED;
    }
}
