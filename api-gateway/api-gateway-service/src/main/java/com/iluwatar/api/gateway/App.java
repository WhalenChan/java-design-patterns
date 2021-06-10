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

package com.iluwatar.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * With the Microservices pattern, a client may need data from multiple different microservices. If
 * the client called each microservice directly, that could contribute to longer load times, since
 * the client would have to make a network request for each microservice called. Moreover, having
 * the client call each microservice directly ties the client to that microservice - if the internal
 * implementations of the microservices change (for example, if two microservices are combined
 * sometime in the future) or if the location (host and port) of a microservice changes, then every
 * client that makes use of those microservices must be updated.
 *
 * <p>The intent of the API Gateway pattern is to alleviate some of these issues. In the API
 * Gateway pattern, an additional entity (the API Gateway) is placed between the client and the
 * microservices. The job of the API Gateway is to aggregate the calls to the microservices. Rather
 * than the client calling each microservice individually, the client calls the API Gateway a single
 * time. The API Gateway then calls each of the microservices that the client needs.
 *
 * <p>This implementation shows what the API Gateway pattern could look like for an e-commerce
 * site. The {@link ApiGateway} makes calls to the Image and Price microservices using the {@link
 * ImageClientImpl} and {@link PriceClientImpl} respectively. Customers viewing the site on a
 * desktop device can see both price information and an image of a product, so the {@link
 * ApiGateway} calls both of the microservices and aggregates the data in the {@link DesktopProduct}
 * model. However, mobile users only see price information; they do not see a product image. For
 * mobile users, the {@link ApiGateway} only retrieves price information, which it uses to populate
 * the {@link MobileProduct}.
 * <p>
 * 和聚合器微服务设计（aggregator-microservices）模式的区别？API网关模式是聚合器微服务模式的实践？
 * <p>使用微服务模式，客户端可能需要来自多个不同微服务的数据。如果客户端直接调用每个微服务，
 * 则可能会导致更长的加载时间，因为客户端必须为每个调用的微服务发出网络请求。 此外，让客户端调用每个微服务直接将客户端
 * 与该微服务联系起来——如果微服务的内部实现发生变化（例如，如果两个微服务在未来某个时候组合）或者微服务的位置（主机和端口）
 * 更改，那么每个使用这些微服务的客户端都必须更新。
 * <p>API 网关模式的目的是缓解其中一些问题。 在 API 网关模式中，在客户端和微服务之间放置了一个额外的实体（API 网关）。
 * API 网关的工作是聚合对微服务的调用。 客户端不是单独调用每个微服务，而是一次调用 API 网关。
 * API 网关然后调用客户端需要的每个微服务。
 * <p>此实现展示了 API 网关模式对于电子商务站点的外观。 {@link ApiGateway} 分别使用 {@link ImageClientImpl} 和 {@link PriceClientImpl} 调用 Image 和 Price 微服务。 在桌面设备上查看网站的客户可以同时看到价格信息和产品图片，因此 {@link ApiGateway} 会调用这两个微服务并在 {@link DesktopProduct} 模型中聚合数据。 但是，移动用户只能看到价格信息； 他们看不到产品图片。 对于移动用户，{@link ApiGateway} 仅检索用于填充 {@link MobileProduct} 的价格信息。
 */
@SpringBootApplication
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
