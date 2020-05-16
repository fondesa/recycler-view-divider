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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Tests of DividersAroundCell.kt file.
 */
class DividersAroundCellKtTest {

    @Test
    fun `dividersAroundCell - vertical grid, 1 column`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(1, 1, 1))

        grid.dividersAroundCell(0).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(1).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(2).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 3, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.VERTICAL)
        )
        assertThrows(IndexOutOfBoundsException::class.java) { grid.dividersAroundCell(3) }
    }

    @Test
    fun `dividersAroundCell - vertical grid, multiple columns, cells with same size`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(2, 2, 2))

        grid.dividersAroundCell(0).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(1).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(2).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(3).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(4).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 3, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(5).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 3, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 2, orientation = Orientation.VERTICAL)
        )
        assertThrows(IndexOutOfBoundsException::class.java) { grid.dividersAroundCell(6) }
    }

    @Test
    fun `dividersAroundCell - vertical grid, multiple columns, cells with different sizes`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 2, 1))

        grid.dividersAroundCell(0).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(1).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(2).assertEquals(
            top = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 3, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(3).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(4).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(5).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 3, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.VERTICAL)
        )
        assertThrows(IndexOutOfBoundsException::class.java) { grid.dividersAroundCell(6) }
    }

    @Test
    fun `dividersAroundCell - horizontal grid, 1 row`() {
        val grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(1, 1, 1))

        grid.dividersAroundCell(0).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(1).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(2).assertEquals(
            top = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 3, originY = 0, orientation = Orientation.VERTICAL)
        )
        assertThrows(IndexOutOfBoundsException::class.java) { grid.dividersAroundCell(3) }
    }

    @Test
    fun `dividersAroundCell - horizontal grid, multiple rows, cells with same size`() {
        val grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(2, 2, 2))

        grid.dividersAroundCell(0).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(1).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(2).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(3).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(4).assertEquals(
            top = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 3, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(5).assertEquals(
            top = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 2, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 3, originY = 1, orientation = Orientation.VERTICAL)
        )
        assertThrows(IndexOutOfBoundsException::class.java) { grid.dividersAroundCell(6) }
    }

    @Test
    fun `dividersAroundCell - horizontal grid, multiple rows, cells with different sizes`() {
        val grid = dummyGrid(orientation = Orientation.HORIZONTAL, cellsInLines = intArrayOf(3, 2, 1))

        grid.dividersAroundCell(0).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(1).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(2).assertEquals(
            top = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 0, originY = 3, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 0, originY = 2, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(3).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(4).assertEquals(
            top = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 1, originY = 2, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 1, originY = 1, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.VERTICAL)
        )
        grid.dividersAroundCell(5).assertEquals(
            top = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.HORIZONTAL),
            bottom = Divider(grid = grid, originX = 2, originY = 1, orientation = Orientation.HORIZONTAL),
            start = Divider(grid = grid, originX = 2, originY = 0, orientation = Orientation.VERTICAL),
            end = Divider(grid = grid, originX = 3, originY = 0, orientation = Orientation.VERTICAL)
        )
        assertThrows(IndexOutOfBoundsException::class.java) { grid.dividersAroundCell(6) }
    }

    private fun SidesMap<Divider>.assertEquals(top: Divider, bottom: Divider, start: Divider, end: Divider) {
        val actualTop = this[Side.TOP]
        val actualBottom = this[Side.BOTTOM]
        val actualStart = this[Side.START]
        val actualEnd = this[Side.END]

        assertNotNull(actualTop)
        assertNotNull(actualBottom)
        assertNotNull(actualStart)
        assertNotNull(actualEnd)
        assertEquals(top, actualTop)
        assertEquals(bottom, actualBottom)
        assertEquals(start, actualStart)
        assertEquals(end, actualEnd)
    }
}
