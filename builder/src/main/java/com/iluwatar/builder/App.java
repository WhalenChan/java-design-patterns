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

package com.iluwatar.builder;

import com.iluwatar.builder.Hero.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * The intention of the Builder pattern is to find a solution to the telescoping constructor
 * anti-pattern. The telescoping constructor anti-pattern occurs when the increase of object
 * constructor parameter combination leads to an exponential list of constructors. Instead of using
 * numerous constructors, the builder pattern uses another object, a builder, that receives each
 * initialization parameter step by step and then returns the resulting constructed object at once.
 *
 * <p>The Builder pattern has another benefit. It can be used for objects that contain flat data
 * (html code, SQL query, X.509 certificate...), that is to say, data that can't be easily edited.
 * This type of data cannot be edited step by step and must be edited at once. The best way to
 * construct such an object is to use a builder class.
 *
 * <p>In this example we have the Builder pattern variation as described by Joshua Bloch in
 * Effective Java 2nd Edition.
 *
 * <p>We want to build {@link Hero} objects, but its construction is complex because of the many
 * parameters needed. To aid the user we introduce {@link Builder} class. {@link Hero.Builder} takes
 * the minimum parameters to build {@link Hero} object in its constructor. After that additional
 * configuration for the {@link Hero} object can be done using the fluent {@link Builder} interface.
 * When configuration is ready the build method is called to receive the final {@link Hero} object.
 *
 * <p>建造者模式的意图是找到一种解决伸缩构造函数反模式的方法。 当对象构造函数参数组合的增加导致构造函数的指数列表时，就会出现伸缩构造函数反模式。
 * 构建器模式没有使用大量的构造器，而是使用另一个对象，一个构建器，它逐步接收每个初始化参数，然后立即返回生成的构造对象。
 *
 * <p>建造者模式还有一个好处。 它可用于包含平面数据（html 代码、SQL 查询、X.509 证书...）的对象，即无法轻松编辑的数据。
 * 这种类型的数据不能一步一步编辑，必须一次性编辑。 构造此类对象的最佳方法是使用构建器类。
 *
 * <p>在这个例子中，我们有 Joshua Bloch 在 Effective Java 2nd Edition 中描述的 Builder 模式变体。
 *
 * <p>我们想构建 {@link Hero} 对象，但由于需要很多参数，它的构建很复杂。 为了帮助用户，我们引入了 {@link Builder} 类。
 * {@link Hero.Builder} 在其构造函数中使用最少的参数来构建 {@link Hero} 对象。 之后，可以使用流畅的 {@link Builder}
 * 界面完成 {@link Hero} 对象的附加配置。 当配置准备好后，会调用 build 方法来接收最终的 {@link Hero} 对象。
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

        var mage = new Hero.Builder(Profession.MAGE, "Riobard")
                .withHairColor(HairColor.BLACK)
                .withWeapon(Weapon.DAGGER)
                .build();
        LOGGER.info(mage.toString());

        var warrior = new Hero.Builder(Profession.WARRIOR, "Amberjill")
                .withHairColor(HairColor.BLOND)
                .withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL).withWeapon(Weapon.SWORD)
                .build();
        LOGGER.info(warrior.toString());

        var thief = new Hero.Builder(Profession.THIEF, "Desmond")
                .withHairType(HairType.BALD)
                .withWeapon(Weapon.BOW)
                .build();
        LOGGER.info(thief.toString());
    }
}
