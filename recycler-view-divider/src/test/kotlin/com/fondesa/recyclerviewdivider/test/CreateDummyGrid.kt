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

package com.fondesa.recyclerviewdivider.test

import com.fondesa.recyclerviewdivider.Cell
import com.fondesa.recyclerviewdivider.Grid
import com.fondesa.recyclerviewdivider.LayoutDirection
import com.fondesa.recyclerviewdivider.Line
import com.fondesa.recyclerviewdivider.Orientation

/**
 * Creates a test [Grid].
 * The span size of each cell will be based on the cells count for each line.
 * If the cells count differs, the lines with more cells define the span count and the other lines fill it increasing the span size
 * of their cells.
 *
 * @param orientation the grid's orientation.
 * @param cellsInLines the array of cells count for each line. The lines count is defined with the array size.
 * E.g. the array [2, 5, 3] creates a grid with 3 lines having 2 cells in the first line, 5 cells in the second line and 3 in the third.
 * @return the grid used in test methods.
 */
internal fun dummyGrid(orientation: Orientation = Orientation.VERTICAL, cellsInLines: IntArray = intArrayOf()): Grid {
    val spanCount = cellsInLines.max() ?: 1
    return Grid(
        spanCount = spanCount,
        orientation = orientation,
        layoutDirection = LayoutDirection(
            horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
            vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
        ),
        lines = cellsInLines.map { cellsCount ->
            val cells = List(cellsCount) { index ->
                val spanSize = if (index == cellsCount - 1) spanCount - cellsCount + 1 else 1
                Cell(spanSize = spanSize)
            }
            Line(cells = cells)
        }
    )
}
