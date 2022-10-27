package org.example;

public class ThreadH {
    public void ad() {
        int numOfThreads = 4;
        for (int i = 0; i < numOfThreads; i++) {
            Worker worker = new Worker(i);
            worker.start();
        }
    }
}
