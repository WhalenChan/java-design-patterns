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

package com.iluwatar.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * Composition over inheritance. The Bridge pattern can also be thought of as two layers of
 * abstraction. With Bridge, you can decouple an abstraction from its implementation so that the two
 * can vary independently.
 *
 * <p>In Bridge pattern both abstraction ({@link Weapon}) and implementation ( {@link Enchantment})
 * have their own class hierarchies. The interface of the implementations can be changed without
 * affecting the clients.
 *
 * <p>In this example we have two class hierarchies. One of weapons and another one of
 * enchantments. We can easily combine any weapon with any enchantment using composition instead of
 * creating deep class hierarchy.
 *
 * 组合优于继承。 桥接模式也可以被认为是两层抽象。 使用 Bridge，您可以将抽象与其实现分离，以便两者可以独立变化。
 * <p>在桥接模式中，抽象（{@link Weapon}）和实现（{@link Enchantment}）都有自己的类层次结构。 可以在不影响客户端的情况下更改实现的接口。
 * <p>在这个例子中，我们有两个类层次结构。 一种武器，另一种附魔。 我们可以使用组合轻松地将任何武器与任何附魔结合起来，而不是创建很深的类层次结构。
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
        //骑士收到一把附魔剑
        LOGGER.info("The knight receives an enchanted sword.");
        var enchantedSword = new Sword(new SoulEatingEnchantment());
        enchantedSword.wield();
        enchantedSword.swing();
        enchantedSword.unwield();

        //女武神收到一个附魔的锤子。
        LOGGER.info("The valkyrie receives an enchanted hammer.");
        var hammer = new Hammer(new FlyingEnchantment());
        hammer.wield();
        hammer.swing();
        hammer.unwield();
    }
}
