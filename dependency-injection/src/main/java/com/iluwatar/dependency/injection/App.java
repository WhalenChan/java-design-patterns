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

package com.iluwatar.dependency.injection;

import com.google.inject.Guice;

/**
 * Dependency Injection pattern deals with how objects handle their dependencies. The pattern
 * implements so called inversion of control principle. Inversion of control has two specific rules:
 * - High-level modules should not depend on low-level modules. Both should depend on abstractions.
 * - Abstractions should not depend on details. Details should depend on abstractions.
 *
 * <p>In this example we show you three different wizards. The first one ({@link SimpleWizard}) is
 * a naive implementation violating the inversion of control principle. It depends directly on a
 * concrete implementation which cannot be changed.
 *
 * <p>The second and third wizards({@link AdvancedWizard} and {@link AdvancedSorceress}) are more
 * flexible. They do not depend on any concrete implementation but abstraction. They utilizes
 * Dependency Injection pattern allowing their {@link Tobacco} dependency to be injected through
 * constructor ({@link AdvancedWizard}) or setter ({@link AdvancedSorceress}). This way, handling
 * the dependency is no longer the wizard's responsibility. It is resolved outside the wizard
 * class.
 *
 * <p>The fourth example takes the pattern a step further. It uses Guice framework for Dependency
 * Injection. {@link TobaccoModule} binds a concrete implementation to abstraction. Injector is then
 * used to create {@link GuiceWizard} object with correct dependencies.
 *
 * 依赖注入模式处理对象如何处理其依赖关系。该模式实现了所谓的控制反转原则。控制反转有两个特定规则：
 * 高级模块不应依赖于低级模块。两者都应依赖于抽象。
 * 抽象不应依赖于细节。细节应依赖于抽象。
 * <p>在这个示例中，我们展示了三个不同的巫师。第一个（{@link SimpleWizard}）是一个违反控制反转原则的简单实现。它直接依赖于一个不能更改的具体实现。
 * <p>第二个和第三个巫师（{@link AdvancedWizard} 和 {@link AdvancedSorceress}）更灵活。它们不依赖于任何具体实现，而是依赖于抽象。
 *
 * 它们利用依赖注入模式，通过构造函数（{@link AdvancedWizard}）或 setter（{@link AdvancedSorceress}）注入它们的 {@link Tobacco} 依赖项。
 * 这样，处理依赖关系不再是巫师的责任，而是在巫师类外部解决。
 *
 * <p>第四个示例更进一步。它使用 Guice 框架进行依赖注入。{@link TobaccoModule} 将具体实现绑定到抽象。
 * 然后使用注入器创建具有正确依赖关系的 {@link GuiceWizard} 对象。
 *
 */
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        var simpleWizard = new SimpleWizard();
        simpleWizard.smoke();

        var advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
        advancedWizard.smoke();

        var advancedSorceress = new AdvancedSorceress();
        advancedSorceress.setTobacco(new SecondBreakfastTobacco());
        advancedSorceress.smoke();

        var injector = Guice.createInjector(new TobaccoModule());
        var guiceWizard = injector.getInstance(GuiceWizard.class);
        guiceWizard.smoke();
    }
}
