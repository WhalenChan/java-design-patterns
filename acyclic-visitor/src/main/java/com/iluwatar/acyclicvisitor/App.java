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

package com.iluwatar.acyclicvisitor;

/**
 * The Acyclic Visitor pattern allows new functions to be added to existing class hierarchies
 * without affecting those hierarchies, and without creating the dependency cycles that are inherent
 * to the GoF Visitor pattern, by making the Visitor base class degenerate
 *
 * <p>In this example the visitor base class is {@link ModemVisitor}. The base class of the visited
 * hierarchy is {@link Modem} and has two children {@link Hayes} and {@link Zoom} each one having
 * its own visitor interface {@link HayesVisitor} and {@link ZoomVisitor} respectively. {@link
 * ConfigureForUnixVisitor} and {@link ConfigureForDosVisitor} implement each derivative's visit
 * method only if it is required
 *
 * <p>
 * 非循环访问者模式允许在不影响类层次结构的情况下向其中添加新的功能，同时又避免了由GoF访问者模式固有的依赖循环。这是通过使访问者基类退化来实现的。
 * 在这个例子中，访问者基类是{@link ModemVisitor}。被访问层次结构的基类是{@link Modem}，
 * 它有两个子类{@link Hayes}和{@link Zoom}，每个子类都有自己的访问者接口{@link HayesVisitor}和{@link ZoomVisitor}。
 * {@link ConfigureForUnixVisitor}和{@link ConfigureForDosVisitor}仅在需要时实现了每个派生类的visit方法。
 * </p>
 * 这个模式结合etc下面的png图片会更好理解一点
 */
public class App {

    /**
     * Program's entry point.
     */
    public static void main(String[] args) {
        var conUnix = new ConfigureForUnixVisitor(); //只有ZoomVisitor的接口能力
        var conDos = new ConfigureForDosVisitor(); //实现了AllZoomVisitor所有接口，具有ZoomVisitor和HayesVisitor的能力

        var zoom = new Zoom();
        var hayes = new Hayes();

        // Hayes modem with Dos configurator
        hayes.accept(conDos);
        // Hayes modem with Unix configurator
        hayes.accept(conUnix); //Only HayesVisitor is allowed to visit Hayes modem - 因为 ConfigureForUnixVisitor 只实现了ZoomVisitor接口

        // Zoom modem with Dos configurator
        zoom.accept(conDos);
        // Zoom modem with Unix configurator
        zoom.accept(conUnix);

    }
}
