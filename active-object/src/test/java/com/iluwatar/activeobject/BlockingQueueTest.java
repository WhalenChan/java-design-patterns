package com.iluwatar.activeobject;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列测试
 *
 * @author chenrunhui
 * @date 2021/6/3 9:07
 */
public class BlockingQueueTest {


    @Test
    void test1() throws Exception {
        BlockingQueue<Runnable> requests = new LinkedBlockingQueue<>();

        Thread thread = new Thread(() -> {
            boolean infinite = true;
            while (infinite) {
                try {
                    requests.take().run(); //take的时候没有值 会阻塞
                    System.out.println("1-----------------------"); //新增一个元素之后这句才会打印
                } catch (InterruptedException e) {
                    infinite = false;
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        Thread.sleep(3 * 1000);
        requests.add(() -> {
            System.out.println("2--测试新增一个元素");
        });
        Thread.sleep(5 * 1000);
    }

    @Test
    void test2() throws Exception {

        Thread t = new MyThread();
        t.start();
        Thread.sleep(2 * 1000);
        System.out.println("发送中断信号=>");
        //具体来说，当对一个线程，调用 interrupt() 时，
        // ① 如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常
        // ② 如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。被设置中断标志的线程将继续正常运行，不受影响。
        t.interrupt();
        Thread.sleep(10 * 1000);
    }

    class MyThread extends Thread {

        boolean flag = true;
        int i = 0;

        @Override
        public void run() {
            while (this.flag) {
                i++;
                System.out.println("-- i =" + i);
                //有这下面这段代码的时候会抛异常 抛完异常之后继续while循环 while循环不会中断
                /*try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.flag = false;
                }*/
                //发送中断信号后在这里判断退出Thread 否则while循环会一直执行
                if (Thread.interrupted()) {
                    //接收到中断信号
                    System.out.println("=>接收到中断信号");
                    this.flag = false;
                    break;
                }
            }
            System.out.println("线程t最后执行的代码");
        }
    }

}
