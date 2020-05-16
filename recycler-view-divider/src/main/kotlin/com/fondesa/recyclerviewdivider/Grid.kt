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

/**
 * Identifies the grid layout of a [LinearLayoutManager]/[GridLayoutManager].
 * The grid pre-calculates all the rows and columns placement of a layout manager.
 * A vertical grid is a grid shown in a layout manager with vertical orientation.
 * An horizontal grid is a grid shown in a layout manager with horizontal orientation.
 *
 * @param spanCount the number of possible columns in a vertical grid or the number of possible rows in an horizontal grid.
 * @param orientation [Orientation.VERTICAL] if the grid is vertical, [Orientation.HORIZONTAL] if the grid is horizontal.
 * @param layoutDirection identifies if the grid is top-to-bottom, right-to-left, bottom-to-top, left-to-right.
 * @param lines the rows in a vertical grid, the columns in an horizontal grid.
 */
data class Grid(
    val spanCount: Int,
    val orientation: Orientation,
    val layoutDirection: LayoutDirection,
    val lines: List<Line>
) {

    /**
     * @return the number of rows in a vertical grid, the number of columns in an horizontal grid.
     */
    val linesCount: Int get() = lines.size
}

/**
 * Identifies the row in a vertical grid or the column in an horizontal grid.
 * A single cell can be larger than a single column for a vertical grid or than a single row for an horizontal grid ([Cell.spanSize]).
 *
 * @param cells the items places in this line.
 */
inline class Line(val cells: List<Cell>) {

    /**
     * @return the number of items in this line.
     */
    val cellsCount: Int get() = cells.size
}

/**
 * Identifies the item of a line.
 * In a vertical grid, the cell can take the space of multiple columns. The number of columns this cell take is defined with [spanSize].
 * In an horizontal grid, the cell can take the space of multiple rows. The number of rows this cell take is defined with [spanSize].
 *
 * @param spanSize the width (number of columns) in a vertical grid, the height (number of rows) in an horizontal grid.
 */
inline class Cell(val spanSize: Int)
