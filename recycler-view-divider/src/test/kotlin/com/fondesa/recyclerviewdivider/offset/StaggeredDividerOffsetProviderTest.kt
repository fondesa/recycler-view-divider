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
import com.fondesa.recyclerviewdivider.LayoutDirection
import com.fondesa.recyclerviewdivider.Orientation
import com.fondesa.recyclerviewdivider.Side
import com.fondesa.recyclerviewdivider.StaggeredCell
import com.fondesa.recyclerviewdivider.StaggeredGrid
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of [StaggeredDividerOffsetProvider].
 */
class StaggeredDividerOffsetProviderTest {

    @Test
    fun `getOffsetFromSize - top divider, side dividers visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - start divider, side dividers visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - bottom divider, side dividers visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - end divider, side dividers visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - top divider, side dividers not visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - start divider, side dividers not visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - bottom divider, side dividers not visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - end divider, side dividers not visible - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - divider on top side, grid vertical - returns 0`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(0, false).getOffsetFromSize(grid, Side.TOP, 10).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - divider on bottom side, grid vertical - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(0, false).getOffsetFromSize(grid, Side.BOTTOM, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - divider on start side, grid horizontal - returns 0`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(0, false).getOffsetFromSize(grid, Side.START, 10).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - divider on end side, grid horizontal - returns full size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(0, false).getOffsetFromSize(grid, Side.END, 10).assertEquals(10)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(8)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(6)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(6)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = true).assertEquals(8)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, odd size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(9)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(7)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(7)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(9)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, 1px size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(0)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(0)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(0)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = true).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid vertical, point-five size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = true).assertEquals(6)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = true).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(8)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(6)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(6)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = true).assertEquals(8)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, odd size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(9)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(7)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(7)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(9)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, 1px size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(0)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(0)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(0)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = true).assertEquals(1)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = true).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, grid horizontal, point-five size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = true).assertEquals(6)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = true).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(8)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(6)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(6)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.END, 10, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.START, 10, areSideDividersVisible = false).assertEquals(8)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, odd size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(9)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(7)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(7)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(9)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, 1px size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(1)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(1)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(1)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.END, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.START, 1, areSideDividersVisible = false).assertEquals(1)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid vertical, point-five size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.VERTICAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.END, 11, areSideDividersVisible = false).assertEquals(6)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.START, 11, areSideDividersVisible = false).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(8)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(6)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(6)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 10, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.TOP, 10, areSideDividersVisible = false).assertEquals(8)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, odd size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(9)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(7)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(4)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(7)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(2)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(9)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, 1px size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 5)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(1)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(1)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(2, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(1)
        StaggeredCell(3, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 1, areSideDividersVisible = false).assertEquals(0)
        StaggeredCell(4, false)
            .getOffsetFromSize(grid, Side.TOP, 1, areSideDividersVisible = false).assertEquals(1)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, grid horizontal, point-five size - returns balanced size`() {
        val grid = dummyStaggeredGrid(Orientation.HORIZONTAL, 2)
        StaggeredCell(0, false)
            .getOffsetFromSize(grid, Side.BOTTOM, 11, areSideDividersVisible = false).assertEquals(6)
        StaggeredCell(1, false)
            .getOffsetFromSize(grid, Side.TOP, 11, areSideDividersVisible = false).assertEquals(5)
    }

    @Px
    private fun StaggeredCell.getOffsetFromSize(
        grid: StaggeredGrid,
        dividerSide: Side,
        @Px size: Int,
        areSideDividersVisible: Boolean = false
    ): Int {
        val provider = StaggeredDividerOffsetProvider(areSideDividersVisible)
        return provider.getOffsetFromSize(grid, this, dividerSide, size)
    }

    private fun dummyStaggeredGrid(orientation: Orientation, spanCount: Int): StaggeredGrid = StaggeredGrid(
        spanCount = spanCount,
        orientation = orientation,
        layoutDirection = LayoutDirection(LayoutDirection.Horizontal.LEFT_TO_RIGHT, LayoutDirection.Vertical.TOP_TO_BOTTOM)
    )

    private fun Int.assertEquals(@Px expected: Int) {
        assertEquals(expected, this)
    }
}
