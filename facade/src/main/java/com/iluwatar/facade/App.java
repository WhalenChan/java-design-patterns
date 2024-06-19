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

package com.iluwatar.facade;

/**
 * The Facade design pattern is often used when a system is very complex or difficult to understand
 * because the system has a large number of interdependent classes or its source code is
 * unavailable. This pattern hides the complexities of the larger system and provides a simpler
 * interface to the client. It typically involves a single wrapper class which contains a set of
 * members required by client. These members access the system on behalf of the facade client and
 * hide the implementation details.
 *
 * <p>In this example the Facade is ({@link DwarvenGoldmineFacade}) and it provides a simpler
 * interface to the goldmine subsystem.
 *
 * 当系统非常复杂或难以理解时，经常使用外观设计模式，因为系统有大量相互依赖的类或其源代码不可用。
 * 该模式隐藏了大型系统的复杂性，并向客户端提供了一个更简单的接口。它通常涉及一个包装类，该类包含客户端所需的一组成员。
 * 这些成员代表外观客户端访问系统，并隐藏实现细节。
 * <p>在这个例子中，外观是 {@link DwarvenGoldmineFacade}，它为金矿子系统提供了一个更简单的接口。</p>
 *
 */
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        var facade = new DwarvenGoldmineFacade();
        facade.startNewDay();
        facade.digOutGold();
        facade.endDay();
    }
}
