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

package com.iluwatar.doubledispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Date: 12/10/15 - 11:31 PM
 *
 * @author Jeroen Meulemeester
 */
class FlamingAsteroidTest extends CollisionTest<FlamingAsteroid> {

    @Override
    final FlamingAsteroid getTestedObject() {
        return new FlamingAsteroid(1, 2, 3, 4);
    }

    /**
     * Test the constructor parameters
     */
    @Test
    void testConstructor() {
        final var asteroid = new FlamingAsteroid(1, 2, 3, 4);
        assertEquals(1, asteroid.getLeft());
        assertEquals(2, asteroid.getTop());
        assertEquals(3, asteroid.getRight());
        assertEquals(4, asteroid.getBottom());
        assertTrue(asteroid.isOnFire());
        assertFalse(asteroid.isDamaged());
        assertEquals("FlamingAsteroid at [1,2,3,4] damaged=false onFire=true", asteroid.toString());
    }

    /**
     * Test what happens we collide with an asteroid
     */
    @Test
    void testCollideFlamingAsteroid() {
        testCollision(
                new FlamingAsteroid(1, 2, 3, 4),
                false, true,
                false, true
        );
    }

    /**
     * Test what happens we collide with an meteoroid
     */
    @Test
    void testCollideMeteoroid() {
        testCollision(
                new Meteoroid(1, 1, 3, 4),
                false, false,
                false, true
        );
    }

    /**
     * Test what happens we collide with ISS
     */
    @Test
    void testCollideSpaceStationIss() {
        testCollision(
                new SpaceStationIss(1, 1, 3, 4),
                true, true,
                false, true
        );
    }

    /**
     * Test what happens we collide with MIR
     */
    @Test
    void testCollideSpaceStationMir() {
        testCollision(
                new SpaceStationMir(1, 1, 3, 4),
                true, true,
                false, true
        );
    }

}