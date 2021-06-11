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

package com.iluwatar.async.method.invocation;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * In this example, we are launching space rockets and deploying lunar rovers.
 *
 * <p>The application demonstrates the async method invocation pattern. The key parts of the
 * pattern are <code>AsyncResult</code> which is an intermediate container for an asynchronously
 * evaluated value, <code>AsyncCallback</code> which can be provided to be executed on task
 * completion and <code>AsyncExecutor</code> that manages the execution of the async tasks.
 *
 * <p>The main method shows example flow of async invocations. The main thread starts multiple
 * tasks with variable durations and then continues its own work. When the main thread has done it's
 * job it collects the results of the async tasks. Two of the tasks are handled with callbacks,
 * meaning the callbacks are executed immediately when the tasks complete.
 *
 * <p>Noteworthy difference of thread usage between the async results and callbacks is that the
 * async results are collected in the main thread but the callbacks are executed within the worker
 * threads. This should be noted when working with thread pools.
 *
 * <p>Java provides its own implementations of async method invocation pattern. FutureTask,
 * CompletableFuture and ExecutorService are the real world implementations of this pattern. But due
 * to the nature of parallel programming, the implementations are not trivial. This example does not
 * take all possible scenarios into account but rather provides a simple version that helps to
 * understand the pattern.
 *

 * <p>异步方法调用模式
 * <p>在这个例子中，我们发射太空火箭并部署月球车。
 * <p>该应用程序演示了异步方法调用模式。 该模式的关键部分是 <code>AsyncResult</code>，它是一个异步计算值的中间容器，
 *    <code>AsyncCallback</code>，它可以在任务完成时执行，<code>AsyncExecutor</code> 管理异步任务的执行。
 * <p>main 方法显示了异步调用的示例流程。 主线程启动多个具有可变持续时间的任务，然后继续自己的工作。 当主线程完成它的工作时，
 * 它会收集异步任务的结果。 其中两个任务使用回调处理，这意味着当任务完成时会立即执行回调。
 * <p>异步结果和回调之间线程使用的显着区别在于异步结果收集在主线程中，而回调在工作线程中执行。 使用线程池时应注意这一点。
 * <p>Java 提供了自己的异步方法调用模式实现。 FutureTask、CompletableFuture 和 ExecutorService
 * 是这种模式的真实世界实现。但由于并行编程的性质，实现不是那么简单。 此示例并未考虑所有可能的场景，而是提供了一个有助于
 * 理解该模式的简单版本。
 *
 * <p>*略难理解，需要多看，复习*
 *
 * @see AsyncResult
 * @see AsyncCallback
 * @see AsyncExecutor
 * @see java.util.concurrent.FutureTask
 * @see java.util.concurrent.CompletableFuture
 * @see java.util.concurrent.ExecutorService
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     */
    public static void main(String[] args) throws Exception {
        // construct a new executor that will run async tasks
        var executor = new ThreadAsyncExecutor();

        // start few async tasks with varying processing times, two last with callback handlers
        final var asyncResult1 = executor.startProcess(lazyval(10, 500));
        final var asyncResult2 = executor.startProcess(lazyval("test", 300));
        final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
        final var asyncResult4 =
                executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"));
        final var asyncResult5 =
                executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

        // emulate processing in the current thread while async tasks are running in their own threads
        Thread.sleep(350); // Oh boy, we are working hard here
        log("Mission command is sipping coffee");

        // wait for completion of the tasks
        final var result1 = executor.endProcess(asyncResult1);
        final var result2 = executor.endProcess(asyncResult2);
        final var result3 = executor.endProcess(asyncResult3);
        //这两个await()有没有结果都一样？
        asyncResult4.await();
        asyncResult5.await();

        // log the results of the tasks, callbacks log immediately when complete
        log("Space rocket <" + result1 + "> launch complete");
        log("Space rocket <" + result2 + "> launch complete");
        log("Space rocket <" + result3 + "> launch complete");
    }

    /**
     * Creates a callable that lazily evaluates to given value with artificial delay.
     *
     * @param value       value to evaluate
     * @param delayMillis artificial delay in milliseconds
     * @return new callable for lazy evaluation
     */
    private static <T> Callable<T> lazyval(T value, long delayMillis) {
        return () -> {
            Thread.sleep(delayMillis);
            log("Space rocket <" + value + "> launched successfully");
            return value;
        };
    }

    /**
     * Creates a simple callback that logs the complete status of the async result.
     *
     * @param name callback name
     * @return new async callback
     */
    private static <T> AsyncCallback<T> callback(String name) {

        return (value, ex) -> {
            if (ex.isPresent()) {
                log(name + " failed: " + ex.map(Exception::getMessage).orElse(""));
            } else {
                //Deploying lunar rover <20>
                //Deploying lunar rover <callback>
                log(name + " <" + value + ">");
            }
        };
    }

    private static void log(String msg) {
        LOGGER.info(msg);
    }
}
