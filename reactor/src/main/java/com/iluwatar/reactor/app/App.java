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

package com.iluwatar.reactor.app;

import com.iluwatar.reactor.framework.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This application demonstrates Reactor pattern. The example demonstrated is a Distributed Logging
 * Service where it listens on multiple TCP or UDP sockets for incoming log requests.
 *
 * <p><i>INTENT</i> <br>
 * The Reactor design pattern handles service requests that are delivered concurrently to an
 * application by one or more clients. The application can register specific handlers for processing
 * which are called by reactor on specific events.
 *
 * <p><i>PROBLEM</i> <br>
 * Server applications in a distributed system must handle multiple clients that send them service
 * requests. Following forces need to be resolved:
 * <ul>
 * <li>Availability</li>
 * <li>Efficiency</li>
 * <li>Programming Simplicity</li>
 * <li>Adaptability</li>
 * </ul>
 *
 * <p><i>PARTICIPANTS</i> <br>
 * <ul>
 * <li>Synchronous Event De-multiplexer
 * <p>
 * {@link NioReactor} plays the role of synchronous event de-multiplexer.
 * It waits for events on multiple channels registered to it in an event loop.
 * </p>
 * </li>
 * <li>Initiation Dispatcher
 * <p>
 * {@link NioReactor} plays this role as the application specific {@link ChannelHandler}s
 * are registered to the reactor.
 * </p>
 * </li>
 * <li>Handle
 * <p>
 * {@link AbstractNioChannel} acts as a handle that is registered to the reactor.
 * When any events occur on a handle, reactor calls the appropriate handler.
 * </p>
 * </li>
 * <li>Event Handler
 * <p>
 * {@link ChannelHandler} acts as an event handler, which is bound to a
 * channel and is called back when any event occurs on any of its associated handles. Application
 * logic resides in event handlers.
 * </p>
 * </li>
 * </ul>
 * The application utilizes single thread to listen for requests on all ports. It does not create a
 * separate thread for each client, which provides better scalability under load (number of clients
 * increase).
 * The example uses Java NIO framework to implement the Reactor.
 *
 * 本应用程序演示了Reactor模式。示例展示了一个分布式日志服务，它监听多个TCP或UDP套接字以接收日志请求。
 * <p><i>意图</i> <br>
 * Reactor设计模式处理由一个或多个客户端并发发送到应用程序的服务请求。应用程序可以注册特定的处理器，由Reactor在特定事件上调用这些处理器进行处理。
 * <p><i>问题</i> <br>
 * 分布式系统中的服务器应用程序必须处理多个客户端发送的服务请求。需要解决以下问题：
 * <ul>
 * <li>可用性</li>
 * <li>效率</li>
 * <li>编程简易性</li>
 * <li>适应性</li>
 * </ul>
 * <p><i>参与者</i> <br>
 * <ul>
 * <li>同步事件分发器
 * <p>
 * {@link NioReactor} 扮演同步事件分发器的角色。它在事件循环中等待在注册到它的多个通道上的事件。
 * </p>
 * </li>
 * <li>启动分发器
 * <p>
 * {@link NioReactor} 扮演这个角色，因为应用程序特定的 {@link ChannelHandler} 注册到Reactor。
 * </p>
 * </li>
 * <li>句柄
 * <p>
 * {@link AbstractNioChannel} 作为注册到Reactor的句柄。当句柄上发生任何事件时，Reactor会调用相应的处理器。
 * </p>
 * </li>
 * <li>事件处理器
 * <p>
 * {@link ChannelHandler} 作为事件处理器，它绑定到一个通道，并在其相关句柄上发生任何事件时被回调。应用逻辑位于事件处理器中。
 * </p>
 * </li>
 * </ul>
 * 应用程序使用单线程监听所有端口上的请求。它不会为每个客户端创建单独的线程，这在负载增加（客户端数量增加）时提供了更好的可扩展性。示例使用Java NIO框架实现Reactor模式。
 */
public class App {

    private NioReactor reactor;
    private final List<AbstractNioChannel> channels = new ArrayList<>();
    private final Dispatcher dispatcher;

    /**
     * Creates an instance of App which will use provided dispatcher for dispatching events on
     * reactor.
     *
     * @param dispatcher the dispatcher that will be used to dispatch events.
     */
    public App(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * App entry.
     */
    public static void main(String[] args) throws IOException {
        new App(new ThreadPoolDispatcher(2)).start();
    }

    /**
     * Starts the NIO reactor.
     *
     * @throws IOException if any channel fails to bind.
     */
    public void start() throws IOException {
        /*
         * The application can customize its event dispatching mechanism.
         */
        reactor = new NioReactor(dispatcher);

        /*
         * This represents application specific business logic that dispatcher will call on appropriate
         * events. These events are read events in our example.
         */
        var loggingHandler = new LoggingHandler();

        /*
         * Our application binds to multiple channels and uses same logging handler to handle incoming
         * log requests.
         */
        reactor
                .registerChannel(tcpChannel(16666, loggingHandler))
                .registerChannel(tcpChannel(16667, loggingHandler))
                .registerChannel(udpChannel(16668, loggingHandler))
                .registerChannel(udpChannel(16669, loggingHandler))
                .start();
    }

    /**
     * Stops the NIO reactor. This is a blocking call.
     *
     * @throws InterruptedException if interrupted while stopping the reactor.
     * @throws IOException          if any I/O error occurs
     */
    public void stop() throws InterruptedException, IOException {
        reactor.stop();
        dispatcher.stop();
        for (var channel : channels) {
            channel.getJavaChannel().close();
        }
    }

    private AbstractNioChannel tcpChannel(int port, ChannelHandler handler) throws IOException {
        var channel = new NioServerSocketChannel(port, handler);
        channel.bind();
        channels.add(channel);
        return channel;
    }

    private AbstractNioChannel udpChannel(int port, ChannelHandler handler) throws IOException {
        var channel = new NioDatagramChannel(port, handler);
        channel.bind();
        channels.add(channel);
        return channel;
    }
}
