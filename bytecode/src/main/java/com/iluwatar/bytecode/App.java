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

package com.iluwatar.bytecode;

import com.iluwatar.bytecode.util.InstructionConverterUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * The intention of Bytecode pattern is to give behavior the flexibility of data by encoding it as
 * instructions for a virtual machine. An instruction set defines the low-level operations that can
 * be performed. A series of instructions is encoded as a sequence of bytes. A virtual machine
 * executes these instructions one at a time, using a stack for intermediate values. By combining
 * instructions, complex high-level behavior can be defined.
 *
 * <p>This pattern should be used when there is a need to define high number of behaviours and
 * implementation engine is not a good choice because It is too lowe level Iterating on it takes too
 * long due to slow compile times or other tooling issues. It has too much trust. If you want to
 * ensure the behavior being defined can’t break the game, you need to sandbox it from the rest of
 * the codebase.
 *
 * <p>字节码模式的目的是通过将数据编码为虚拟机的指令来为行为提供数据的灵活性。 指令集定义了可以执行的低级操作。 一系列指令被编码为字节序列。
 * 虚拟机一次一条地执行这些指令，将堆栈用于中间值。 通过组合指令，可以定义复杂的高级行为。
 *
 * <p>当需要定义大量行为并且实现引擎不是一个好的选择时，应该使用这种模式，因为它的级别太低，由于编译时间慢或其他工具问题，迭代时间太长。
 * 它有太多的信任。 如果您想确保定义的行为不会破坏游戏，您需要将其与代码库的其余部分进行沙箱化。
 *
 */
@Slf4j
public class App {

    /**
     * Main app method.
     *
     * @param args command line args
     */
    public static void main(String[] args) {

        var vm = new VirtualMachine(
                new Wizard(45, 7, 11, 0, 0),
                new Wizard(36, 18, 8, 0, 0));

        vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
        vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
        vm.execute(InstructionConverterUtil.convertToByteCode("GET_HEALTH"));
        vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
        vm.execute(InstructionConverterUtil.convertToByteCode("GET_AGILITY"));
        vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
        vm.execute(InstructionConverterUtil.convertToByteCode("GET_WISDOM"));
        vm.execute(InstructionConverterUtil.convertToByteCode("ADD"));
        vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 2"));
        vm.execute(InstructionConverterUtil.convertToByteCode("DIVIDE"));
        vm.execute(InstructionConverterUtil.convertToByteCode("ADD"));
        vm.execute(InstructionConverterUtil.convertToByteCode("SET_HEALTH"));
    }
}
