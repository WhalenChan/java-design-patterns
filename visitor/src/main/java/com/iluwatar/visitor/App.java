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

package com.iluwatar.visitor;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Visitor pattern defines mechanism to apply operations on nodes in hierarchy. New operations
 * can be added without altering the node interface.</p>
 *
 * <p>In this example there is a unit hierarchy beginning from {@link Commander}. This hierarchy is
 * traversed by visitors. {@link SoldierVisitor} applies its operation on {@link Soldier}s, {@link
 * SergeantVisitor} on {@link Sergeant}s and so on.</p>
 *
 * <p>访问者模式定义了一种在层次结构的节点上应用操作的机制。可以在不修改节点接口的情况下添加新的操作。</p>
 * <p>在这个例子中，有一个从 {@link Commander} 开始的单元层次结构。访问者遍历这个层次结构。{@link SoldierVisitor}
 * 对 {@link Soldier} 施加其操作，{@link SergeantVisitor} 对 {@link Sergeant} 施加其操作，等等。</p>
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {

        var commander = new Commander(
                new Sergeant(new Soldier(), new Soldier(), new Soldier()),
                new Sergeant(new Soldier(), new Soldier(), new Soldier())
        );
        LOGGER.info("------0------");
        commander.accept(new SoldierVisitor());
        LOGGER.info("-----1-----");
        commander.accept(new SergeantVisitor());
        LOGGER.info("-----2-----");
        commander.accept(new CommanderVisitor());

    }
}
