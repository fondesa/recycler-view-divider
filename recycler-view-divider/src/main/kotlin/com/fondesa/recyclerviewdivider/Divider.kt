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
 * Identifies a divider originating from a corner of a cell and going to an adjacent corner.
 * The coordinates:
 * - are zero-based
 * - do NOT depend on the grid's orientation
 * - do depend on the layout orientation
 * The set of coordinates in a grid identifies the intersections in a grid between two lines.
 * The divider is always directing towards a greater coordinate value.
 * For example: if a divider has an [originX] of 1, a [originY] of 2 and it's horizontal, it means that divider is the line
 * between the points (x)1;(y)2 and (x)2;(y)2.
 *
 * Some examples of a list with 3 items:
 * ```
 * top-to-bottom        top-to-bottom        bottom-to-top        bottom-to-top
 * left-to-right        right-to-left        left-to-right        right-to-left
 *
 * 0;0 ----> 1;0        1;0 <---- 0;0        0;3 ----> 1;3        1;3 <---- 0;3
 *  |         |          |         |          ∧         ∧          ∧         ∧
 *  |    0    |          |    0    |          |    2    |          |    2    |
 *  ∨         ∨          ∨         ∨          |         |          |         |
 * 0;1 ----> 1;1        1;1 <---- 0;1        0;2 ----> 1;2        1;2 <---- 0;2
 *  |         |          |         |          ∧         ∧          ∧         ∧
 *  |    1    |          |    1    |          |    1    |          |    1    |
 *  ∨         ∨          ∨         ∨          |         |          |         |
 * 0;2 ----> 1;2        1;2 <---- 0;2        0;1 ----> 1;1        1;1 <---- 0;1
 *  |         |          |         |          ∧         ∧          ∧         ∧
 *  |    2    |          |    2    |          |    0    |          |    0    |
 *  ∨         ∨          ∨         ∨          |         |          |         |
 * 0;3 ----> 1;3        1;3 <---- 0;3        0;0 ----> 1;0        1;0 <---- 0;0
 * ```
 *
 * Some examples of a 2 x 2 grid with 4 items:
 * ```
 *      top-to-bottom                  top-to-bottom                 bottom-to-top                 bottom-to-top
 *      left-to-right                  right-to-left                 left-to-right                 right-to-left
 *
 * 0;0 ----> 1;0 ----> 2;0        2;0 <---- 1;0 <---- 0;0       0;2 ----> 1;2 ----> 2;2       2;2 <---- 1;2 <---- 0;2
 *  |         |         |          |         |         |         ∧         ∧         ∧         ∧         ∧         ∧
 *  |    0    |    1    |          |    1    |    0    |         |    2    |    3    |         |    3    |    2    |
 *  ∨         ∨         ∨          ∨         ∨         ∨         |         |         |         |         |         |
 * 0;1 ----> 1;1 ----> 2;1        2;1 <---- 1;1 <---- 0;1       0;1 ----> 1;1 ----> 2;1       2;1 <---- 1;1 <---- 0;1
 *  |         |         |          |         |         |         ∧         ∧         ∧         ∧         ∧         ∧
 *  |    2    |    3    |          |    3    |    2    |         |    0    |    1    |         |    1    |    0    |
 *  ∨         ∨         ∨          ∨         ∨         ∨         |         |         |         |         |         |
 * 0;2 ----> 1;2 ----> 2;2        2;2 <---- 1;2 <---- 0;2       0;0 ----> 1;0 ----> 2;0       2;0 <---- 1;0 <---- 0;0
 * ```
 *
 * The set of coordinates is NOT a projection of the intersections of all the lines in a grid N (rows) x M (columns),
 * but they map exactly the given grid, considering the cells' spans.
 * The coordinates match their projection only when all the items have a span size of 1.
 * This decision was taken only to simplify the customization of a single divider with the different providers,
 * even if it's less coherent with the structure of a standard table.
 *
 * An example of a grid with 4 items having different span sizes:
 * ```
 *           top-to-bottom
 *           left-to-right
 *
 * 0;0 ----> 1;0 --------------> 2;0
 *  |         |                   |
 *  |    0    |         1         |
 *  ∨         ∨                   ∨
 * 0;1 ----> 1;1 ----> 1;1 ----> 2;1
 *  |                   |         |
 *  |         2         |    3    |
 *  ∨                   ∨         ∨
 * 0;2 --------------> 1;2 ----> 2;2
 * ```
 * The coordinate (x)1;(y)1 is repeated twice since it's the bottom divider of the item with index 0 and the top divider
 * of the item with index 2.
 *
 * @param grid the grid in which this divider should appear.
 * @param originX the x-coordinate from which the divider is originating.
 * @param originY the y-coordinate from which the divider is originating.
 * @param orientation [Orientation.HORIZONTAL] if the divider is horizontal, [Orientation.VERTICAL] if the divider is vertical.
 * @see Grid
 * @see Line
 * @see Cell
 */
public data class Divider(
    private val grid: Grid,
    val originX: Int,
    val originY: Int,
    val orientation: Orientation
) {

    /**
     * @return true if the divider is partially or totally the top side of the grid.
     * If the layout of the grid is reversed vertically, the top divider is considered the one at the bottom since
     * all the cells start from the bottom of the grid.
     */
    val isTopDivider: Boolean get() = orientation.isHorizontal && originY == 0

    /**
     * @return true if the divider is partially or totally the bottom side of the grid.
     * If the layout of the grid is reversed vertically, the bottom divider is considered the one at the top since
     * all the cells start from the bottom of the grid.
     */
    val isBottomDivider: Boolean
        get() {
            if (orientation.isVertical) return false
            if (isGridVertical) return originY == grid.linesCount
            val line = grid.lines[originX]
            return originY == line.cellsCount && line.isFilled
        }

    /**
     * @return true if the divider is partially or totally the start (left in left-to-right, right in right-to-left) side of the grid.
     * If the layout of the grid is reversed horizontally, the start divider is considered the one at the end since
     * all the cells start from the end of the grid.
     */
    val isStartDivider: Boolean get() = orientation.isVertical && originX == 0

    /**
     * @return true if the divider is partially or totally the end (right in left-to-right, left in right-to-left) side of the grid.
     * If the layout of the grid is reversed horizontally, the end divider is considered the one at the start since
     * all the cells start from the end of the grid.
     */
    val isEndDivider: Boolean
        get() {
            if (orientation.isHorizontal) return false
            if (isGridHorizontal) return originX == grid.linesCount
            val line = grid.lines[originY]
            return originX == line.cellsCount && line.isFilled
        }

    /**
     * @return true if the divider is partially or totally the first divider.
     * The first divider can be:
     * - a divider before the first row in a vertical grid
     * - a divider before the first column in an horizontal grid.
     */
    val isFirstDivider: Boolean get() = if (isGridVertical) isTopDivider else isStartDivider

    /**
     * @return true if the divider is partially or totally the last divider.
     * The last divider can be:
     * - a divider after the last row in a vertical grid
     * - a divider after the last column in an horizontal grid.
     */
    val isLastDivider: Boolean get() = if (isGridVertical) isBottomDivider else isEndDivider

    /**
     * @return true if the divider is partially or totally a side divider.
     * A side divider can be:
     * - a divider before the first column in a vertical grid
     * - a divider after the last column in a vertical grid
     * - a divider before the first row in an horizontal grid
     * - a divider after the last row in an horizontal grid
     */
    val isSideDivider: Boolean get() = if (isGridVertical) isStartDivider || isEndDivider else isTopDivider || isBottomDivider

    /**
     * @return the total span size of the cells before this divider in its line.
     */
    internal val accumulatedSpan: Int
        get() {
            require(orientation == grid.orientation) {
                "The accumulated span can be calculated only if the divider has the same orientation of its grid."
            }
            val lineIndex: Int
            val cellIndex: Int
            if (isGridVertical) {
                lineIndex = originY
                cellIndex = originX
            } else {
                lineIndex = originX
                cellIndex = originY
            }
            val line = grid.lines[lineIndex]
            return (0 until cellIndex).sumOf { i -> line.cells[i].spanSize }
        }

    private val isGridVertical: Boolean get() = grid.orientation.isVertical

    private val isGridHorizontal: Boolean get() = grid.orientation.isHorizontal

    private val Line.isFilled: Boolean get() = cells.sumOf { it.spanSize } == grid.spanCount
}
