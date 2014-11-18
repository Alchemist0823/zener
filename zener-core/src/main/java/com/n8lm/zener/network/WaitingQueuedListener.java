package com.n8lm.zener.network;

import com.esotericsoftware.kryonet.Listener;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by sunz5 on 11/17/2014.
 */
public class WaitingQueuedListener extends Listener.QueuedListener {

    protected final Queue<Runnable> runnables;

    public WaitingQueuedListener(Listener listener) {
        super(listener);
        this.runnables = new LinkedList<>();
    }

    public synchronized void queue(Runnable runnable) {
        this.runnables.add(runnable);
    }

    public synchronized boolean isEmpty() {
        return runnables.isEmpty();
    }

    public synchronized Runnable pop() {
        return this.runnables.poll();
    }
}
