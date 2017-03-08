package com.mophsic.concurrent;

import jdk.nashorn.internal.ir.WhileNode;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaofei
 * @date 2017/2/23
 */
public class ToastOMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue(),
                butteredQueue = new ToastQueue(),
                finishedQueue = new ToastQueue();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Toaster(dryQueue));
        executorService.execute(new Buttered(dryQueue, butteredQueue));
        executorService.execute(new Jammer(butteredQueue, finishedQueue));
        executorService.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}

class Toast {

    public enum Status {DRY, BUTTERED, JAMMED}

    private Status status = Status.DRY;
    private final int id;

    Toast(int idn) {
        id = idn;
    }

    void butter() {
        this.status = Status.BUTTERED;
    }

    void jam() {
        this.status = Status.JAMMED;
    }

    int getId() {
        return id;
    }

    Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Toast " + id + " : " + status;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {
}

class Toaster implements Runnable {

    private ToastQueue queue;
    private int count = 0;
    private Random rand = new Random(47);

    Toaster(ToastQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                Toast t = new Toast(count++);
                System.out.println(t);
                queue.put(t);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Toaster OFF");
    }
}

class Buttered implements Runnable {

    private ToastQueue dryQueue, butteredQueue;

    Buttered(ToastQueue dryQueue, ToastQueue butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Toast t = dryQueue.take();
                t.butter();
                System.out.println(t);
                butteredQueue.put(t);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        System.out.println("Buttered OFF");
    }
}

class Jammer implements Runnable {
    private ToastQueue butteredQueue, finishedQueue;

    Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Toast t = butteredQueue.take();
                t.jam();
                System.out.println(t);
                finishedQueue.put(t);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().isInterrupted());
        }
        System.out.println("Jammed OFF");
    }
}

class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int count = 0;

    Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Toast t = finishedQueue.take();
                if (t.getId() != count++ || t.getStatus() != Toast.Status.JAMMED) {
                    System.out.println("Error " + t);
                    System.exit(1);
                } else {
                    System.out.println("Chomp! " + t);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Eater OFF");
    }
}

