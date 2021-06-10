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

package com.iluwatar.ambassador;

/**
 * The ambassador pattern creates a helper service that sends network requests on behalf of a
 * client. It is often used in cloud-based applications to offload features of a remote service.
 *
 * <p>An ambassador service can be thought of as an out-of-process proxy that is co-located with
 * the client. Similar to the proxy design pattern, the ambassador service provides an interface for
 * another remote service. In addition to the interface, the ambassador provides extra functionality
 * and features, specifically offloaded common connectivity tasks. This usually consists of
 * monitoring, logging, routing, security etc. This is extremely useful in legacy applications where
 * the codebase is difficult to modify and allows for improvements in the application's networking
 * capabilities.
 *
 * <p>In this example, we will the ({@link ServiceAmbassador}) class represents the ambassador while
 * the
 * ({@link RemoteService}) class represents a remote application.
 *
 * <p>大使模式创建一个帮助程序服务，代表客户端发送网络请求。它通常用于基于云的应用程序来卸载远程服务的功能。
 * <p>大使服务可以被认为是与客户端位于同一位置的进程外代理。类似于代理设计模式，大使服务为另一个远程服务提供了一个接口。
 * 除了界面之外，大使还提供额外的功能和特性，特别是卸载常见的连接任务。 这通常包括监控、日志记录、路由、安全等。
 * 这在代码库难以修改的遗留应用程序中非常有用，并且允许改进应用程序的网络功能。
 * <p>在此示例中，我们将 ({@link ServiceAmbassador}) 类表示大使，而 ({@link RemoteService}) 类表示远程应用程序。
 */
public class App {

    /**
     * Entry point.
     */
    public static void main(String[] args) {
        var host1 = new Client();
        var host2 = new Client();
        host1.useService(12);
        host2.useService(73);
    }
}
