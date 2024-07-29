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

package com.iluwatar.gameloop;

/**
 * The variable-step game loop chooses a time step to advance based on how much
 * real time passed since the last frame. The longer the frame takes, the bigger
 * steps the game takes. It always keeps up with real time because it will take
 * bigger and bigger steps to get there.
 *
 * 可变步长游戏循环根据自上一帧以来经过的实际时间选择前进的时间步长。帧持续时间越长，游戏的步长就越大。
 * 它总是跟上实际时间，因为它会采取越来越大的步长来达到这一点。
 */
public class VariableStepGameLoop extends GameLoop {

    @Override
    protected void processGameLoop() {
        var lastFrameTime = System.currentTimeMillis();
        while (isGameRunning()) {
            processInput();
            var currentFrameTime = System.currentTimeMillis();
            var elapsedTime = currentFrameTime - lastFrameTime;
            update(elapsedTime);
            lastFrameTime = currentFrameTime;
            render();
        }
    }

    protected void update(Long elapsedTime) {
        controller.moveBullet(0.5f * elapsedTime / 1000);
    }

}
