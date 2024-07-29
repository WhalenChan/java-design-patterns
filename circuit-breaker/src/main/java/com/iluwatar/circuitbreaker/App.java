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

package com.iluwatar.circuitbreaker;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * The intention of the Circuit Builder pattern is to handle remote failures robustly, which is to
 * mean that if a service is dependant on n number of other services, and m of them fail, we should
 * be able to recover from that failure by ensuring that the user can still use the services that
 * are actually functional, and resources are not tied up by uselessly by the services which are not
 * working. However, we should also be able to detect when any of the m failing services become
 * operational again, so that we can use it
 * </p>
 * <p>
 * In this example, the circuit breaker pattern is demonstrated by using three services: {@link
 * DelayedRemoteService}, {@link QuickRemoteService} and {@link MonitoringService}. The monitoring
 * service is responsible for calling three services: a local service, a quick remove service
 * {@link QuickRemoteService} and a delayed remote service {@link DelayedRemoteService} , and by
 * using the circuit breaker construction we ensure that if the call to remote service is going to
 * fail, we are going to save our resources and not make the function call at all, by wrapping our
 * call to the remote services in the {@link DefaultCircuitBreaker} implementation object.
 * </p>
 * <p>
 * This works as follows: The {@link DefaultCircuitBreaker} object can be in one of three states:
 * <b>Open</b>, <b>Closed</b> and <b>Half-Open</b>, which represents the real world circuits. If
 * the state is closed (initial), we assume everything is alright and perform the function call.
 * However, every time the call fails, we note it and once it crosses a threshold, we set the state
 * to Open, preventing any further calls to the remote server. Then, after a certain retry period
 * (during which we expect thee service to recover), we make another call to the remote server and
 * this state is called the Half-Open state, where it stays till the service is down, and once it
 * recovers, it goes back to the closed state and the cycle continues.
 * </p>
 *
 * <p>
 * 电路构建器模式的目的是稳健地处理远程故障，这意味着如果一个服务依赖于多个其他服务中的 n 个，并且其中的 m 个失败，我们应该能够从这种故障中恢复，
 * 确保用户仍然可以使用实际功能的服务，并且资源不会被无法工作的服务无谓地占用。然而，我们还应该能够检测到任何一个失败的服务何时恢复正常，以便我们可以重新使用它。
 * </p>
 * <p>
 * 在这个例子中，通过使用三个服务演示了断路器模式：{@link DelayedRemoteService}、{@link QuickRemoteService} 和 {@link MonitoringService}。
 * 监控服务负责调用三个服务：一个本地服务，一个快速远程服务 {@link QuickRemoteService} 和一个延迟远程服务 {@link DelayedRemoteService}，
 *并通过使用断路器构造，确保如果对远程服务的调用将要失败，我们会保存我们的资源，并且完全不进行函数调用，通过将我们的远程服务调用包装在 {@link DefaultCircuitBreaker}
 * 实现对象中。
 * </p>
 * <p>
 * 工作原理如下：{@link DefaultCircuitBreaker} 对象可以处于三种状态之一：<b>Open</b>（打开）、<b>Closed</b>（关闭）和 <b>Half-Open</b>（半开），
 * 这代表了现实世界的电路。如果状态是关闭的（初始状态），我们假设一切正常并执行函数调用。然而，每次调用失败时，我们都会记录下来，一旦超过阈值，
 * 我们将状态设置为打开，阻止任何进一步的远程服务器调用。然后，在一定的重试期（期间我们期望服务恢复）后，我们再次调用远程服务器，此状态称为半开状态，
 * 它将保持到服务恢复正常，一旦恢复正常，它会回到关闭状态，并且循环继续。
 * </p>
 *
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {

        var serverStartTime = System.nanoTime();

        var delayedService = new DelayedRemoteService(serverStartTime, 5);
        var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000, 2,
                2000 * 1000 * 1000);

        var quickService = new QuickRemoteService();
        var quickServiceCircuitBreaker = new DefaultCircuitBreaker(quickService, 3000, 2,
                2000 * 1000 * 1000);

        //Create an object of monitoring service which makes both local and remote calls
        var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,
                quickServiceCircuitBreaker);

        //Fetch response from local resource
        LOGGER.info(monitoringService.localResourceResponse());

        //Fetch response from delayed service 2 times, to meet the failure threshold
        LOGGER.info(monitoringService.delayedServiceResponse());
        LOGGER.info(monitoringService.delayedServiceResponse());

        //Fetch current state of delayed service circuit breaker after crossing failure threshold limit
        //which is OPEN now
        LOGGER.info(delayedServiceCircuitBreaker.getState());

        //Meanwhile, the delayed service is down, fetch response from the healthy quick service
        LOGGER.info(monitoringService.quickServiceResponse());
        LOGGER.info(quickServiceCircuitBreaker.getState());

        //Wait for the delayed service to become responsive
        try {
            LOGGER.info("Waiting for delayed service to become responsive");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Check the state of delayed circuit breaker, should be HALF_OPEN
        LOGGER.info(delayedServiceCircuitBreaker.getState());

        //Fetch response from delayed service, which should be healthy by now
        LOGGER.info(monitoringService.delayedServiceResponse());
        //As successful response is fetched, it should be CLOSED again.
        LOGGER.info(delayedServiceCircuitBreaker.getState());
    }
}
