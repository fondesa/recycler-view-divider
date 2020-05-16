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

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Creates a [Grid] from a [LinearLayoutManager]/[GridLayoutManager].
 *
 * @param itemCount the number of the items in the [RecyclerView].
 * @return the grid layout of the given layout manager.
 * @see Grid
 */
internal fun LinearLayoutManager.grid(itemCount: Int): Grid =
    if (this is GridLayoutManager && spanCount > 1) multipleSpanGrid(itemCount) else singleSpanGrid(itemCount)

private fun LinearLayoutManager.singleSpanGrid(itemCount: Int): Grid {
    val rows = List(itemCount) { Line(cells = listOf(Cell(spanSize = 1))) }
    return Grid(spanCount = 1, orientation = layoutOrientation, layoutDirection = obtainLayoutDirection(), lines = rows)
}

private fun GridLayoutManager.multipleSpanGrid(itemCount: Int): Grid {
    val spanCount = spanCount
    val spanSizeLookup = spanSizeLookup
    val lines = mutableListOf<Line>()
    var cellsInLine = mutableListOf<Cell>()
    (0 until itemCount).forEach { index ->
        // The span-index is always 0 on the first item so it should be skipped, otherwise an empty row would be added.
        if (index != 0 && spanSizeLookup.getSpanIndex(index, spanCount) == 0) {
            // The item with index [index] is in a new row so the previous row is created with the old cells.
            lines += Line(cells = cellsInLine)
            // Since it's a new row, the cells list must be reset.
            cellsInLine = mutableListOf()
        }
        // Every item consists in a new cell.
        cellsInLine.add(Cell(spanSize = spanSizeLookup.getSpanSize(index)))
        // The last row must be created with the collected cells.
        if (index == itemCount - 1) {
            lines += Line(cells = cellsInLine)
        }
    }
    return Grid(spanCount = spanCount, orientation = layoutOrientation, layoutDirection = obtainLayoutDirection(), lines = lines)
}
