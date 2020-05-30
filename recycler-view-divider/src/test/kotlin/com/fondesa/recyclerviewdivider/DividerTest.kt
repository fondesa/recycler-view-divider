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

import com.fondesa.recyclerviewdivider.test.dummyGrid
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests of [Divider].
 */
class DividerTest {

    @Test
    fun `init - properties return constructor value`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 3,
            originY = 4,
            orientation = Orientation.VERTICAL
        )

        assertEquals(3, divider.originX)
        assertEquals(4, divider.originY)
        assertEquals(Orientation.VERTICAL, divider.orientation)
    }

    @Test
    fun `isTopDivider - divider vertical - returns false`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isTopDivider)
    }

    @Test
    fun `isTopDivider - divider horizontal, originY not 0 - returns false`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 0,
            originY = 1,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isTopDivider)
    }

    @Test
    fun `isTopDivider - divider horizontal, originY not 0 - returns true`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isTopDivider)
    }

    @Test
    fun `isBottomDivider - divider vertical - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(1, 1)),
            originX = 0,
            originY = 2,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isBottomDivider)
    }

    @Test
    fun `isBottomDivider - divider horizontal, grid vertical, originY not equal to lines count - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(1, 1)),
            originX = 0,
            originY = 1,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isBottomDivider)
    }

    @Test
    fun `isBottomDivider - divider horizontal, grid vertical, originY equal to lines count - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(1, 1)),
            originX = 0,
            originY = 2,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isBottomDivider)
    }

    @Test
    fun `isBottomDivider - divider horizontal, grid horizontal, originY not equal to cells count in given line - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 2,
            originY = 2,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isBottomDivider)
    }

    @Test
    fun `isBottomDivider - divider horizontal, grid horizontal, originY equal to cells count in given line, line not filled - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(3, 2, 2), fillLine = false),
            originX = 2,
            originY = 2,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isBottomDivider)
    }

    @Test
    fun `isBottomDivider - divider horizontal, grid horizontal, originY equal to cells count in given line, line filled - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(3, 2, 2)),
            originX = 2,
            originY = 2,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isBottomDivider)
    }

    @Test
    fun `isStartDivider - divider horizontal - returns false`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isStartDivider)
    }

    @Test
    fun `isStartDivider - divider vertical, originX not 0 - returns false`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 1,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isStartDivider)
    }

    @Test
    fun `isStartDivider - divider vertical, originX 0 - returns true`() {
        val divider = Divider(
            grid = dummyGrid(),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isStartDivider)
    }

    @Test
    fun `isEndDivider - divider horizontal - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(1, 1)),
            originX = 2,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isEndDivider)
    }

    @Test
    fun `isEndDivider - divider vertical, grid horizontal, originX not equal to lines count - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(1, 1)),
            originX = 1,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isEndDivider)
    }

    @Test
    fun `isEndDivider - divider vertical, grid horizontal, originX equal to lines count - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(1, 1)),
            originX = 2,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isEndDivider)
    }

    @Test
    fun `isEndDivider - divider vertical, grid vertical, originX not equal to cells count in given line - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 2,
            originY = 2,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isEndDivider)
    }

    @Test
    fun `isEndDivider - divider vertical, grid vertical, originX equal to cells count in given line, line not filled - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 2, 2), fillLine = false),
            originX = 2,
            originY = 2,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isEndDivider)
    }

    @Test
    fun `isEndDivider - divider vertical, grid vertical, originX equal to cells count in given line, line filled - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 3,
            originY = 2,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isEndDivider)
    }

    @Test
    fun `isFirstDivider - divider not top, grid vertical - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isFirstDivider)
    }

    @Test
    fun `isFirstDivider - divider top, grid vertical - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isFirstDivider)
    }

    @Test
    fun `isFirstDivider - divider not start, grid horizontal - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isFirstDivider)
    }

    @Test
    fun `isFirstDivider - divider start, grid horizontal - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isFirstDivider)
    }

    @Test
    fun `isLastDivider - divider not bottom, grid vertical - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(1, 1)),
            originX = 0,
            originY = 1,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isLastDivider)
    }

    @Test
    fun `isLastDivider - divider bottom divider, grid vertical - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(1, 1)),
            originX = 0,
            originY = 2,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isLastDivider)
    }

    @Test
    fun `isLastDivider - divider not end, grid horizontal - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(1, 1)),
            originX = 1,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isLastDivider)
    }

    @Test
    fun `isLastDivider - divider not end, grid horizontal - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(1, 1)),
            originX = 2,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isLastDivider)
    }

    @Test
    fun `isSideDivider - divider not start or end, grid vertical - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertFalse(divider.isSideDivider)
    }

    @Test
    fun `isSideDivider - divider start, grid vertical - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isSideDivider)
    }

    @Test
    fun `isSideDivider - divider end, grid vertical - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 3,
            originY = 2,
            orientation = Orientation.VERTICAL
        )

        assertTrue(divider.isSideDivider)
    }

    @Test
    fun `isSideDivider - divider not top or bottom divider, grid horizontal - returns false`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        )

        assertFalse(divider.isSideDivider)
    }

    @Test
    fun `isSideDivider - divider not top divider, grid horizontal - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isSideDivider)
    }

    @Test
    fun `isSideDivider - divider not bottom divider, grid horizontal - returns true`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 2,
            originY = 3,
            orientation = Orientation.HORIZONTAL
        )

        assertTrue(divider.isSideDivider)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `accumulatedSpan - divider horizontal, grid vertical - throws exception`() {
        Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.HORIZONTAL
        ).accumulatedSpan
    }

    @Test(expected = IllegalArgumentException::class)
    fun `accumulatedSpan - divider vertical, grid horizontal - throws exception`() {
        Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL),
            originX = 0,
            originY = 0,
            orientation = Orientation.VERTICAL
        ).accumulatedSpan
    }

    @Test
    fun `accumulatedSpan - divider vertical, grid vertical - returns accumulated span`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 2,
            originY = 1,
            orientation = Orientation.VERTICAL
        )

        assertEquals(3, divider.accumulatedSpan)
    }

    @Test
    fun `accumulatedSpan - divider horizontal, grid horizontal - returns accumulated span`() {
        val divider = Divider(
            grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(3, 2, 3)),
            originX = 1,
            originY = 2,
            orientation = Orientation.HORIZONTAL
        )

        assertEquals(3, divider.accumulatedSpan)
    }
}
