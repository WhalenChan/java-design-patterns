/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.balking;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * In Balking Design Pattern if an object’s method is invoked when it is in an inappropriate state,
 * then the method will return without doing anything. Objects that use this pattern are generally
 * only in a state that is prone to balking temporarily but for an unknown amount of time
 *
 * <p>In this example implementation, {@link WashingMachine} is an object that has two states in
 * which it can be: ENABLED and WASHING. If the machine is ENABLED, the state changes to WASHING
 * using a thread-safe method. On the other hand, if it already has been washing and any other
 * thread executes {@link WashingMachine#wash()} it won't do that and returns without doing
 * anything.
 *
 * 慢行设计模式：
 * 在 Balking(犹豫不决)设计模式中，如果一个对象的方法在它处于不适当的状态时被调用，那么该方法将返回而不做任何事情。
 * 使用此模式的对象通常仅处于易于暂时停止但持续时间未知的状态
 * <p>
 * 在此示例实现中，{@link WashingMachine} 是一个具有两种状态的对象：启用和洗涤。 如果机器已启用，则使用线程安全方法将状态更改为 WASHING。
 * 另一方面，如果它已经在清洗并且任何其他线程执行 {@link WashingMachine#wash()} 它不会这样做并返回而不做任何事情。
 *
 */
@Slf4j
public class App {

    /**
     * Entry Point.
     *
     * @param args the command line arguments - not used
     */
    public static void main(String... args) {
        final var washingMachine = new WashingMachine();
        var executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(washingMachine::wash);
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            LOGGER.error("ERROR: Waiting on executor service shutdown!");
            Thread.currentThread().interrupt();
        }
    }
}
