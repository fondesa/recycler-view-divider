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
import com.fondesa.recyclerviewdivider.Side
import com.fondesa.recyclerviewdivider.StaggeredCell
import com.fondesa.recyclerviewdivider.StaggeredGrid
import com.fondesa.recyclerviewdivider.sidesAdjacentToCell

/**
 * Provides the offset of a [StaggeredCell]'s divider.
 *
 * @param areSideDividersVisible true if the side dividers are visible.
 */
internal class StaggeredDividerOffsetProvider(private val areSideDividersVisible: Boolean) {

    /**
     * Gets the divider's offset.
     * Useful to balance the size of the items in a grid with multiple columns/rows.
     *
     * @param grid the [StaggeredGrid] in which the divider is shown.
     * @param cell the [StaggeredCell] of which the divider is shown.
     * @param dividerSide the side of the cell on which the divider is shown.
     * @param size the size of the divider.
     * @return the offset which should be rendered by the given divider.
     */
    @Px
    fun getOffsetFromSize(grid: StaggeredGrid, cell: StaggeredCell, dividerSide: Side, @Px size: Int): Int {
        // The dividers adjacent to the grid should always have the same offset.
        val sidesAdjacentToCell = grid.sidesAdjacentToCell(cell)
        val isAdjacentToGrid = dividerSide in sidesAdjacentToCell
        if (isAdjacentToGrid && areSideDividersVisible) return size
        if (isAdjacentToGrid) return 0
        val gridOrientation = grid.orientation
        // The balancing should be done only if the divider has the same orientation of the grid since it's useless
        // to balance a divider when its container is a scrolling view like a RecyclerView.
        if (gridOrientation.isVertical && dividerSide == Side.TOP || gridOrientation.isHorizontal && dividerSide == Side.START) return 0
        if (gridOrientation.isVertical && dividerSide == Side.BOTTOM || gridOrientation.isHorizontal && dividerSide == Side.END) return size
        val spanCount = grid.spanCount
        val spanIndex = cell.spanIndex
        return normalizedOffsetFromSize(dividerSide, size, spanCount, spanIndex, areSideDividersVisible)
    }
}
