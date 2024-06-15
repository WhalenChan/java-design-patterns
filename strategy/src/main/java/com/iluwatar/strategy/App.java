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

package com.iluwatar.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>The Strategy pattern (also known as the policy pattern) is a software design pattern that
 * enables an algorithm's behavior to be selected at runtime.</p>
 *
 * <p>Before Java 8 the Strategies needed to be separate classes forcing the developer
 * to write lots of boilerplate code. With modern Java it is easy to pass behavior
 * with method references and lambdas making the code shorter and more readable.</p>
 *
 * <p>In this example ({@link DragonSlayingStrategy}) encapsulates an algorithm. The containing
 * object ({@link DragonSlayer}) can alter its behavior by changing its strategy.</p>
 *
 *
 * <p>策略模式（也称为政策模式）是一种软件设计模式，可以在运行时选择算法的行为。</p>
 * <p>在Java 8之前，策略需要是单独的类，这迫使开发者编写大量样板代码。使用现代Java，通过方法引用和lambda表达式传递行为变得容易，使代码更简洁和可读。</p>
 * <p>在这个例子中，{@link DragonSlayingStrategy} 封装了一个算法。包含对象 {@link DragonSlayer} 可以通过改变其策略来改变其行为。</p>
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        // GoF Strategy pattern
        LOGGER.info("青龙在前方！Green dragon spotted ahead!");
        var dragonSlayer = new DragonSlayer(new MeleeStrategy());
        dragonSlayer.goToBattle();
        LOGGER.info("红龙出现了！Red dragon emerges.");
        dragonSlayer.changeStrategy(new ProjectileStrategy());
        dragonSlayer.goToBattle();
        LOGGER.info("黑龙在你面前降落！Black dragon lands before you.");
        dragonSlayer.changeStrategy(new SpellStrategy());
        dragonSlayer.goToBattle();

        // Java 8 functional implementation Strategy pattern
        LOGGER.info("Green dragon spotted ahead!");
        dragonSlayer = new DragonSlayer(
                () -> LOGGER.info("With your Excalibur you severe the dragon's head!"));
        dragonSlayer.goToBattle();

        LOGGER.info("Red dragon emerges.");
        dragonSlayer.changeStrategy(() -> LOGGER.info(
                "You shoot the dragon with the magical crossbow and it falls dead on the ground!"));
        dragonSlayer.goToBattle();

        LOGGER.info("Black dragon lands before you.");
        dragonSlayer.changeStrategy(() -> LOGGER.info(
                "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));
        dragonSlayer.goToBattle();

        // Java 8 lambda implementation with enum Strategy pattern
        LOGGER.info("Green dragon spotted ahead!");
        dragonSlayer.changeStrategy(LambdaStrategy.Strategy.MeleeStrategy);
        dragonSlayer.goToBattle();
        LOGGER.info("Red dragon emerges.");
        dragonSlayer.changeStrategy(LambdaStrategy.Strategy.ProjectileStrategy);
        dragonSlayer.goToBattle();
        LOGGER.info("Black dragon lands before you.");
        dragonSlayer.changeStrategy(LambdaStrategy.Strategy.SpellStrategy);
        dragonSlayer.goToBattle();
    }
}
