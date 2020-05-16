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

/**
 * Gets the [Divider] around the cell at the given index.
 *
 * @param absoluteCellIndex the index of the cell.
 * @return a map containing a [Divider] for each side of the the cell.
 */
internal fun Grid.dividersAroundCell(absoluteCellIndex: Int): SidesMap<Divider> {
    val (lineIndexInGrid, cellIndexInLine) = lineAndCellRelativeIndexesOf(absoluteCellIndex)
    val sidesMap = sidesMapOf<Divider>()
    if (orientation.isVertical) {
        sidesMap[Side.START] = divider(originX = cellIndexInLine, originY = lineIndexInGrid, orientation = Orientation.VERTICAL)
        sidesMap[Side.TOP] = divider(originX = cellIndexInLine, originY = lineIndexInGrid, orientation = Orientation.HORIZONTAL)
        sidesMap[Side.END] = divider(originX = cellIndexInLine + 1, originY = lineIndexInGrid, orientation = Orientation.VERTICAL)
        sidesMap[Side.BOTTOM] = divider(originX = cellIndexInLine, originY = lineIndexInGrid + 1, orientation = Orientation.HORIZONTAL)
    } else {
        sidesMap[Side.START] = divider(originX = lineIndexInGrid, originY = cellIndexInLine, orientation = Orientation.VERTICAL)
        sidesMap[Side.TOP] = divider(originX = lineIndexInGrid, originY = cellIndexInLine, orientation = Orientation.HORIZONTAL)
        sidesMap[Side.END] = divider(originX = lineIndexInGrid + 1, originY = cellIndexInLine, orientation = Orientation.VERTICAL)
        sidesMap[Side.BOTTOM] = divider(originX = lineIndexInGrid, originY = cellIndexInLine + 1, orientation = Orientation.HORIZONTAL)
    }
    return sidesMap
}

private fun Grid.divider(originX: Int, originY: Int, orientation: Orientation): Divider =
    Divider(grid = this, originX = originX, originY = originY, orientation = orientation)

private fun Grid.lineAndCellRelativeIndexesOf(absoluteCellIndex: Int): Pair<Int, Int> {
    var accumulatedCellsCount = 0
    lines.forEachIndexed { lineIndex, line ->
        val relativeIndexInsideLine = absoluteCellIndex - accumulatedCellsCount
        accumulatedCellsCount += line.cells.size
        if (accumulatedCellsCount > absoluteCellIndex) {
            // The index is in the current line.
            return lineIndex to relativeIndexInsideLine
        }
    }
    throw IndexOutOfBoundsException("The grid doesn't contain the item at position $absoluteCellIndex.")
}
