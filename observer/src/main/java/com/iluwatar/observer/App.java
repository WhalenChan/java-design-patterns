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

package com.iluwatar.observer;

import com.iluwatar.observer.generic.GHobbits;
import com.iluwatar.observer.generic.GOrcs;
import com.iluwatar.observer.generic.GWeather;
import lombok.extern.slf4j.Slf4j;

/**
 * The Observer pattern is a software design pattern in which an object, called the subject,
 * maintains a list of its dependents, called observers, and notifies them automatically of any
 * state changes, usually by calling one of their methods. It is mainly used to implement
 * distributed event handling systems. The Observer pattern is also a key part in the familiar
 * model–view–controller (MVC) architectural pattern. The Observer pattern is implemented in
 * numerous programming libraries and systems, including almost all GUI toolkits.
 *
 * <p>In this example {@link Weather} has a state that can be observed. The {@link Orcs} and {@link
 * Hobbits} register as observers and receive notifications when the {@link Weather} changes.
 *
 * 观察者模式是一种软件设计模式，其中一个称为主体的对象维护一个其依赖者（称为观察者）的列表，并在状态发生任何变化时自动通知它们，
 * 通常是通过调用它们的方法。它主要用于实现分布式事件处理系统。观察者模式也是熟悉的模型–视图–控制器（MVC）架构模式中的关键部分。
 * 几乎所有的GUI工具包都实现了观察者模式。<p>在这个例子中，{@link Weather} 有一个可以被观察的状态。
 * {@link Orcs} 和 {@link Hobbits} 注册为观察者，并在 {@link Weather} 发生变化时接收通知。
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {

        var weather = new Weather();
        weather.addObserver(new Orcs());
        weather.addObserver(new Hobbits());

        weather.timePasses();
        weather.timePasses();
        weather.timePasses();
        weather.timePasses();

        // Generic observer inspired by Java Generics and Collection by Naftalin & Wadler
        LOGGER.info("--Running generic version--");
        var genericWeather = new GWeather();
        genericWeather.addObserver(new GOrcs());
        genericWeather.addObserver(new GHobbits());

        genericWeather.timePasses();
        genericWeather.timePasses();
        genericWeather.timePasses();
        genericWeather.timePasses();
    }
}
