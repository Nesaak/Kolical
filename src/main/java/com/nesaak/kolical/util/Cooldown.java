package com.nesaak.kolical.util;

import java.util.concurrent.TimeUnit;

public class Cooldown {

    private long lastuse = 0;
    private long length;

    public Cooldown(long length) {
        this.length = length;
    }

    public Cooldown(TimeUnit unit, long length) {
        this(unit.toMillis(length));
    }

    public long getLastuse() {
        return lastuse;
    }

    public long getLength() {
        return length;
    }

    public long getTimeElapsed() {
        return System.currentTimeMillis() - lastuse;
    }

    public long getTimeRemaining() {
        return Math.max(0, length - getTimeElapsed());
    }

    public boolean isReady() {
        return getTimeElapsed() > length;
    }

    public void use() {
        lastuse = System.currentTimeMillis();
    }

    public boolean useIfReady() {
        long now = System.currentTimeMillis();
        if (now - lastuse > length) {
            lastuse = now;
            return true;
        }
        return false;
    }
}
