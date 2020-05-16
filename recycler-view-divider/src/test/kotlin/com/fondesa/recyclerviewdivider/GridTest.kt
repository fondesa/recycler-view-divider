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
import org.junit.Test

/**
 * Tests of [Grid].
 */
class GridTest {

    @Test
    fun `init - properties return constructor value`() {
        val grid = Grid(
            spanCount = 1,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(Line(cells = listOf(Cell(spanSize = 2))))
        )
        val line = Line(cells = listOf(Cell(spanSize = 1)))
        val cell = Cell(spanSize = 3)

        assertEquals(1, grid.spanCount)
        assertEquals(Orientation.HORIZONTAL, grid.orientation)
        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            grid.layoutDirection
        )
        assertEquals(listOf(Line(cells = listOf(Cell(2)))), grid.lines)
        assertEquals(listOf(Cell(spanSize = 1)), line.cells)
        assertEquals(3, cell.spanSize)
    }

    @Test
    fun `linesCount - no lines - returns 0`() {
        val grid = Grid(
            spanCount = 4,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = emptyList()
        )

        assertEquals(0, grid.linesCount)
    }

    @Test
    fun `linesCount - more than one line - returns number of lines`() {
        val grid = Grid(
            spanCount = 4,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(Line(cells = emptyList()), Line(cells = emptyList()), Line(cells = emptyList()))
        )

        assertEquals(3, grid.linesCount)
    }

    @Test
    fun `cellsCount - no cells in line - returns 0`() {
        val line = Line(cells = emptyList())

        assertEquals(0, line.cellsCount)
    }

    @Test
    fun `cellsCount - more than one cell in line - returns the number of cells`() {
        val line = Line(cells = listOf(Cell(spanSize = 3), Cell(spanSize = 2), Cell(spanSize = 1)))

        assertEquals(3, line.cellsCount)
    }
}
