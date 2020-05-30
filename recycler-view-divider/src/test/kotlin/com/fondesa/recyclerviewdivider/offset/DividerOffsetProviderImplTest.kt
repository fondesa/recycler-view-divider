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

package com.fondesa.recyclerviewdivider.offset

import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.Cell
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid
import com.fondesa.recyclerviewdivider.LayoutDirection
import com.fondesa.recyclerviewdivider.Line
import com.fondesa.recyclerviewdivider.Orientation
import com.fondesa.recyclerviewdivider.Side
import com.fondesa.recyclerviewdivider.test.dummyGrid
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of [DividerOffsetProviderImpl].
 */
class DividerOffsetProviderImplTest {

    @Test
    fun `getOffsetFromSize - top divider - returns full size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2, 2))
        Divider(grid, 1, 0, Orientation.HORIZONTAL).getOffsetFromSize(grid, Side.TOP, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - start divider - returns full size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2, 2))
        Divider(grid, 0, 1, Orientation.VERTICAL).getOffsetFromSize(grid, Side.START, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - bottom divider - returns full size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2, 2))
        Divider(grid, 1, 2, Orientation.HORIZONTAL).getOffsetFromSize(grid, Side.BOTTOM, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - end divider - returns full size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2, 2))
        Divider(grid, 2, 1, Orientation.VERTICAL).getOffsetFromSize(grid, Side.END, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - divider on top side, grid vertical - returns 0`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2, 2))
        Divider(grid, 1, 1, Orientation.HORIZONTAL).getOffsetFromSize(grid, Side.TOP, 10).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - divider on bottom side, grid vertical - returns full size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2, 2))
        Divider(grid, 1, 1, Orientation.HORIZONTAL).getOffsetFromSize(grid, Side.BOTTOM, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - divider on start side, grid horizontal - returns 0`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(2, 2))
        Divider(grid, 1, 1, Orientation.VERTICAL).getOffsetFromSize(grid, Side.START, 10).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - divider on end side, grid horizontal - returns full size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(2, 2))
        Divider(grid, 1, 1, Orientation.VERTICAL).getOffsetFromSize(grid, Side.END, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, same span - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(5))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(2)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(8)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(8)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, different span - returns balanced size`() {
        val grid = Grid(
            orientation = Orientation.VERTICAL,
            spanCount = 5,
            layoutDirection = LayoutDirection(LayoutDirection.Horizontal.LEFT_TO_RIGHT, LayoutDirection.Vertical.TOP_TO_BOTTOM),
            lines = listOf(Line(listOf(Cell(1), Cell(2), Cell(2))))
        )
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(2)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(8)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(4)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, odd size - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(5))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(2)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(9)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(7)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(7)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(9)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, 1px size - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(5))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(0)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(0)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(0)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, point-five size - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, same span - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(5))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(2)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(8)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(8)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, different span - returns balanced size`() {
        val grid = Grid(
            orientation = Orientation.HORIZONTAL,
            spanCount = 5,
            layoutDirection = LayoutDirection(LayoutDirection.Horizontal.LEFT_TO_RIGHT, LayoutDirection.Vertical.TOP_TO_BOTTOM),
            lines = listOf(Line(listOf(Cell(1), Cell(2), Cell(2))))
        )
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(2)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(8)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(4)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, odd size - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(5))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(2)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(9)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(7)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(7)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(4)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(9)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, 1px size - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(5))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(0)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(0)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(0)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(1)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, point-five size - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(2))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(6)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, same span - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(5))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(8)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(6)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(6)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(8)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, different span - returns balanced size`() {
        val grid = Grid(
            orientation = Orientation.VERTICAL,
            spanCount = 5,
            layoutDirection = LayoutDirection(LayoutDirection.Horizontal.LEFT_TO_RIGHT, LayoutDirection.Vertical.TOP_TO_BOTTOM),
            lines = listOf(Line(listOf(Cell(1), Cell(2), Cell(2))))
        )
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(8)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(6)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, odd size - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(5))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(9)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(7)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(7)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(9)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, 1px size - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(5))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(1)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(1)
        Divider(grid, 2, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 3, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(1)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 4, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(1)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, point-five size - returns balanced size`() {
        val grid = dummyGrid(Orientation.VERTICAL, intArrayOf(2))
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(6)
        Divider(grid, 1, 0, Orientation.VERTICAL)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, same span - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(5))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(8)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(6)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(6)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(8)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, different span - returns balanced size`() {
        val grid = Grid(
            orientation = Orientation.HORIZONTAL,
            spanCount = 5,
            layoutDirection = LayoutDirection(LayoutDirection.Horizontal.LEFT_TO_RIGHT, LayoutDirection.Vertical.TOP_TO_BOTTOM),
            lines = listOf(Line(listOf(Cell(1), Cell(2), Cell(2))))
        )
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(8)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(6)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, odd size - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(5))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(9)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(7)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(4)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(7)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(2)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(9)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, 1px size - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(5))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(1)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(1)
        Divider(grid, 0, 2, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 0, 3, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(1)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(0)
        Divider(grid, 0, 4, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(1)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, point-five size - returns balanced size`() {
        val grid = dummyGrid(Orientation.HORIZONTAL, intArrayOf(2))
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(6)
        Divider(grid, 0, 1, Orientation.HORIZONTAL)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(5)
    }

    @Px
    private fun Divider.getOffsetFromSize(
        grid: Grid,
        dividerSide: Side,
        @Px size: Int,
        areSideDividersVisible: Boolean = false
    ): Int {
        val provider = DividerOffsetProviderImpl(areSideDividersVisible)
        return provider.getOffsetFromSize(grid, this, dividerSide, size)
    }

    private fun Int.assertEquals(@Px expected: Int) {
        assertEquals(expected, this)
    }
}
