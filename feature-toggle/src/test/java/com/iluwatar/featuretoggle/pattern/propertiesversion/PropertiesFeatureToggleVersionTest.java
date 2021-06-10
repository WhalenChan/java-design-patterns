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

package com.iluwatar.featuretoggle.pattern.propertiesversion;

import com.iluwatar.featuretoggle.user.User;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Properties Toggle
 */
class PropertiesFeatureToggleVersionTest {

    @Test
    void testNullPropertiesPassed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PropertiesFeatureToggleVersion(null);
        });
    }

    @Test
    void testNonBooleanProperty() {
        assertThrows(IllegalArgumentException.class, () -> {
            final var properties = new Properties();
            properties.setProperty("enhancedWelcome", "Something");
            new PropertiesFeatureToggleVersion(properties);
        });
    }

    @Test
    void testFeatureTurnedOn() {
        final var properties = new Properties();
        properties.put("enhancedWelcome", true);
        var service = new PropertiesFeatureToggleVersion(properties);
        assertTrue(service.isEnhanced());
        final var welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
        assertEquals("Welcome Jamie No Code. You're using the enhanced welcome message.", welcomeMessage);
    }

    @Test
    void testFeatureTurnedOff() {
        final var properties = new Properties();
        properties.put("enhancedWelcome", false);
        var service = new PropertiesFeatureToggleVersion(properties);
        assertFalse(service.isEnhanced());
        final var welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
        assertEquals("Welcome to the application.", welcomeMessage);
    }
}
