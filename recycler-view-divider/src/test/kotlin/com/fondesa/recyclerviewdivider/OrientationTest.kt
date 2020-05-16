/*
 * Copyright (c) 2020 Giorgio Antonioli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fondesa.recyclerviewdivider

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests of [Orientation].
 */
class OrientationTest {

    @Test
    fun `isVertical - Orientation$VERTICAL - returns true`() {
        val orientation = Orientation.VERTICAL

        assertTrue(orientation.isVertical)
    }

    @Test
    fun `isVertical - Orientation$HORIZONTAL - returns false`() {
        val orientation = Orientation.HORIZONTAL

        assertFalse(orientation.isVertical)
    }

    @Test
    fun `isHorizontal - Orientation$VERTICAL - returns false`() {
        val orientation = Orientation.VERTICAL

        assertFalse(orientation.isHorizontal)
    }

    @Test
    fun `isHorizontal - Orientation$HORIZONTAL - returns true`() {
        val orientation = Orientation.HORIZONTAL

        assertTrue(orientation.isHorizontal)
    }
}
