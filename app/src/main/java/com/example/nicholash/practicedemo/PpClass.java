package com.example.nicholash.practicedemo;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nicholas.Huang
 * @Declaration: 此练习是生产者消费者demo，不属安卓程序一部份。 右键run
 * @Email: kurode@sina.cn
 * <p>
 * 2018/1/31 15:53
 **/
public class PpClass {
    Stack<Integer> items = new Stack<>();
    final static int NO_ITEMS = 10;

    public static void main(String args[]) {
        System.out.println("beat the shit out of you!!!!");
        PpClass pc = new PpClass();
        Thread t1 = new Thread(pc.new Producer());
        Consumer consumer = pc.new Consumer();
        Thread t2 = new Thread(consumer, "t2");
        Thread t3 = new Thread(consumer, "t3");
        Thread t4 = new Thread(consumer, "t4");
//        Thread t2 = new Thread(pc.new Consumer());
//        Thread t3 = new Thread(pc.new Consumer());
//        Thread t4 = new Thread(pc.new Consumer());
        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        t3.start();
        t4.start();
        try {
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Producer implements Runnable {

        public void produce(int i) {
            items.push(new Integer(i));
            System.out.println("生产中 " + i + " 当前size还有：" + items.size());
        }

        @Override
        public void run() {
            int i = 0;
            while (i++ < NO_ITEMS) {
                synchronized (items) {
                    produce(i);
                    items.notifyAll();
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Consumer implements Runnable {
        AtomicInteger consumed = new AtomicInteger();

        public void consumer() {
            if (!items.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + "消费了 " + items.pop() + " 当前size还有：" + items.size() + "\r\n" + "=========================");
                consumed.incrementAndGet();
            }
        }

        private boolean theEnd() {
            return consumed.get() >= NO_ITEMS;
        }

        @Override
        public void run() {
            while (!theEnd()) {
                synchronized (items) {
                    while (items.isEmpty() && (!theEnd())) {
                        try {
                            items.wait(100);
                        } catch (Exception e) {
                            Thread.interrupted();
                        }
                    }
                    consumer();
                }
            }
        }
    }
}
