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
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid
import com.fondesa.recyclerviewdivider.Side

/**
 * Implementation of [DividerOffsetProvider] which tries to balance the offsets between the items to render an equal size for each cell.
 *
 * @param areSideDividersVisible true if the side dividers are visible.
 */
internal class DividerOffsetProviderImpl(private val areSideDividersVisible: Boolean) : DividerOffsetProvider {

    @Px
    override fun getOffsetFromSize(grid: Grid, divider: Divider, dividerSide: Side, @Px size: Int): Int {
        // The dividers adjacent to the grid should always have the same offset.
        if (divider.isTopDivider || divider.isBottomDivider || divider.isStartDivider || divider.isEndDivider) return size
        val gridOrientation = grid.orientation
        // The balancing should be done only if the divider has the same orientation of the grid since it's useless
        // to balance a divider when its container is a scrolling view like a RecyclerView.
        if (gridOrientation.isVertical && dividerSide == Side.TOP || gridOrientation.isHorizontal && dividerSide == Side.START) return 0
        if (gridOrientation.isVertical && dividerSide == Side.BOTTOM || gridOrientation.isHorizontal && dividerSide == Side.END) return size
        val spanCount = grid.spanCount
        val spanIndex = divider.spanIndex(dividerSide)
        return normalizedOffsetFromSize(dividerSide, size, spanCount, spanIndex, areSideDividersVisible)
    }

    private fun Divider.spanIndex(dividerSide: Side): Int = when (dividerSide) {
        Side.START, Side.TOP -> accumulatedSpan
        Side.END, Side.BOTTOM -> accumulatedSpan - 1
    }
}
