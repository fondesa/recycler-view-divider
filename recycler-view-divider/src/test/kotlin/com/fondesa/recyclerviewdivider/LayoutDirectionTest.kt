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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests of [LayoutDirection].
 */
class LayoutDirectionTest {

    @Test
    fun `init - properties return constructor value`() {
        val layoutDirection = LayoutDirection(
            horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
            vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
        )

        assertEquals(LayoutDirection.Horizontal.LEFT_TO_RIGHT, layoutDirection.horizontal)
        assertEquals(LayoutDirection.Vertical.TOP_TO_BOTTOM, layoutDirection.vertical)
    }

    @Test
    fun `isRightToLeft - horizontal direction left-to-right - returns false`() {
        val direction = LayoutDirection(
            horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
            vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
        )

        assertFalse(direction.isRightToLeft)
    }

    @Test
    fun `isRightToLeft - horizontal direction right-to-left - returns true`() {
        val direction = LayoutDirection(
            horizontal = LayoutDirection.Horizontal.RIGHT_TO_LEFT,
            vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
        )

        assertTrue(direction.isRightToLeft)
    }

    @Test
    fun `isBottomToTop - vertical direction top-to-bottom - returns false`() {
        val direction = LayoutDirection(
            horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
            vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
        )

        assertFalse(direction.isBottomToTop)
    }

    @Test
    fun `isBottomToTop - vertical direction bottom-to-top - returns true`() {
        val direction = LayoutDirection(
            horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
            vertical = LayoutDirection.Vertical.BOTTOM_TO_TOP
        )

        assertTrue(direction.isBottomToTop)
    }
}
