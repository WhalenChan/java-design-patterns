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

package com.iluwatar.command;

/**
 * The Command pattern is a behavioral design pattern in which an object is used to encapsulate all
 * information needed to perform an action or trigger an event at a later time. This information
 * includes the method name, the object that owns the method and values for the method parameters.
 *
 * <p>Four terms always associated with the command pattern are command, receiver, invoker and
 * client. A command object (spell) knows about the receiver (target) and invokes a method of the
 * receiver. An invoker object (wizard) receives a reference to the command to be executed and
 * optionally does bookkeeping about the command execution. The invoker does not know anything
 * about how the command is executed. The client decides which commands to execute at which
 * points. To execute a command, it passes a reference of the function to the invoker object.
 *
 * <p>In other words, in this example the wizard casts spells on the goblin. The wizard keeps track
 * of the previous spells cast, so it is easy to undo them. In addition, the wizard keeps track of
 * the spells undone, so they can be redone.
 *
 * <p>goblin:地精，妖怪；spells：法术；wizard：巫师；
 *
 * <p>命令模式是一种行为设计模式，其中对象用于封装执行操作或稍后触发事件所需的所有信息。 此信息包括方法名称、拥有该方法的对象和方法参数的值。
 *
 * <p>始终与命令模式相关联的四个术语是命令、接收者、调用者和客户端。 命令对象（咒语）知道接收者（目标）并调用接收者的方法。
 * 调用程序对象（向导）接收对要执行的命令的引用，并可选择对命令执行进行簿记。 调用者对命令的执行方式一无所知。
 * 客户端决定在哪些点执行哪些命令。 为了执行命令，它将函数的引用传递给调用者对象。
 *
 * <p>换句话说，在这个例子中，巫师对妖精施法。 巫师会跟踪之前施放的法术，因此很容易撤销它们。
 * 此外，巫师会跟踪已撤消的法术，以便可以重做。
 */
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        var wizard = new Wizard();
        var goblin = new Goblin();
        //地精初始状态
        goblin.printStatus(); //[size=normal] [visibility=visible]
        //施法 改变大小
        wizard.castSpell(goblin::changeSize);
        goblin.printStatus(); //[size=small] [visibility=visible]

        //施法 改变隐身状态
        wizard.castSpell(goblin::changeVisibility);
        goblin.printStatus(); //[size=small] [visibility=invisible]

        //取消上次施法
        wizard.undoLastSpell();
        goblin.printStatus(); //[size=small] [visibility=visible]

        //取消上上次施法
        wizard.undoLastSpell();
        goblin.printStatus(); //[size=normal] [visibility=visible]

        //重新上一次施法
        wizard.redoLastSpell();
        goblin.printStatus(); //[size=small] [visibility=visible]
        //重新上一次施法
        wizard.redoLastSpell();
        goblin.printStatus(); //[size=small] [visibility=invisible]
    }
}
