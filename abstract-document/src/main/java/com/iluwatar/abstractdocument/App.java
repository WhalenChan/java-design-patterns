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

package com.iluwatar.abstractdocument;

import com.iluwatar.abstractdocument.domain.Car;
import com.iluwatar.abstractdocument.domain.enums.Property;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * The Abstract Document pattern enables handling additional, non-static properties. This pattern
 * uses concept of traits to enable type safety and separate properties of different classes into
 * set of interfaces.
 *
 * <p>In Abstract Document pattern,({@link AbstractDocument}) fully implements {@link Document})
 * interface. Traits are then defined to enable access to properties in usual, static way.
 * </p>
 * <p>
 * 抽象文档模式可以处理额外的非静态属性。该模式使用特征的概念来实现类型安全并将不同类的属性分离到一组接口中。
 * <p>
 * 在抽象文档模式，({@link AbstractDocument}) 实现了 {@link Document})
 * 的所有接口，然后定义特征以允许以通常的静态方式访问属性。
 * </p>
 * <p>Traits：特征，特点</p>
 * <p>这篇文章写得较好，可帮助理解 https://www.cnblogs.com/evan-liang/p/12233924.html</p>
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        LOGGER.info("Constructing parts and car");

        //创建车轮的所有属性
        var wheelProperties = Map.of(
                Property.TYPE.toString(), "wheel",
                Property.MODEL.toString(), "15C",
                Property.WEIGHT.toString(), "10Kg",
                Property.PRICE.toString(), 100L);

        //创建车门的所有属性
        var doorProperties = Map.of(
                Property.TYPE.toString(), "door",
                Property.MODEL.toString(), "Lambo",
                Property.WEIGHT.toString(), "20Kg",
                Property.PRICE.toString(), 300L);
        //创建小车的所有属性
        var carProperties = Map.of(
                Property.MODEL.toString(), "300SL",
                Property.WEIGHT.toString(), "100Kg",
                Property.PRICE.toString(), 10000L,
                Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

        //创建小车的所有属性
        var car = new Car(carProperties);

        LOGGER.info("Here is our car:");
        LOGGER.info("-> model: {}", car.getModel().orElseThrow());
        LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
        LOGGER.info("-> weight: {}", car.getWeight().orElseThrow());
        LOGGER.info("-> parts: ");
        car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}/{}",
                p.getType().orElse(null),
                p.getModel().orElse(null),
                p.getWeight().orElse(null),
                p.getPrice().orElse(null))
        );
    }
}
