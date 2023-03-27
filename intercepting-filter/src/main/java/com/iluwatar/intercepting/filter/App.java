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

package com.iluwatar.intercepting.filter;

/**
 * When a request enters a Web application, it often must pass several entrance tests prior to the
 * main processing stage. For example, - Has the client been authenticated? - Does the client have a
 * valid session? - Is the client's IP address from a trusted network? - Does the request path
 * violate any constraints? - What encoding does the client use to send the data? - Do we support
 * the browser type of the client? Some of these checks are tests, resulting in a yes or no answer
 * that determines whether processing will continue. Other checks manipulate the incoming data
 * stream into a form suitable for processing.
 *
 * <p>The classic solution consists of a series of conditional checks, with any failed check
 * aborting the request. Nested if/else statements are a standard strategy, but this solution leads
 * to code fragility and a copy-and-paste style of programming, because the flow of the filtering
 * and the action of the filters is compiled into the application.
 *
 * <p>The key to solving this problem in a flexible and unobtrusive manner is to have a simple
 * mechanism for adding and removing processing components, in which each component completes a
 * specific filtering action. This is the Intercepting Filter pattern in action.
 *
 * <p>In this example we check whether the order request is valid through pre-processing done via
 * {@link Filter}. Each field has its own corresponding {@link Filter}.
 *
 * @author joshzambales
 *
 *
 * 当请求进入Web应用程序时，通常必须通过几个入口测试才能进入主处理阶段。例如： - 客户端是否已进行身份验证？ - 客户端是否有有效的会话？ - 客户端的IP地址是否来自受信任的网络？ - 请求路径是否违反任何约束？ - 客户端使用什么编码来发送数据？ - 我们是否支持客户端的浏览器类型？其中一些检查是测试，结果是“是”或“否”答案，确定是否继续处理。其他检查将传入的数据流操作为适合处理的形式。
 *
 * 经典的解决方案由一系列条件检查组成，任何失败的检查都会中止请求。嵌套的if/else语句是一种标准策略，但这种解决方案会导致代码脆弱性和复制粘贴式编程，因为过滤器的流程和操作被编译到应用程序中。
 *
 * 在灵活且不显眼的方式下解决这个问题的关键是拥有一个简单的机制，用于添加和删除处理组件，其中每个组件完成特定的过滤操作。这就是拦截过滤器（Intercepting Filter）模式的实现。
 *
 * 在这个示例中，我们通过{@link Filter}进行预处理来检查订单请求是否有效。每个字段都有其对应的{@link Filter}。
 *
 */
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        var filterManager = new FilterManager();
        filterManager.addFilter(new NameFilter());
        filterManager.addFilter(new ContactFilter());
        filterManager.addFilter(new AddressFilter());
        filterManager.addFilter(new DepositFilter());
        filterManager.addFilter(new OrderFilter());

        var client = new Client();
        client.setFilterManager(filterManager);
    }
}
