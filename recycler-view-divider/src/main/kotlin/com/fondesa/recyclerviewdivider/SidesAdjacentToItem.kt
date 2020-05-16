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

import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Gets the sides of the grid adjacent to the given item.
 * When the grid is vertical only [Side.START] and [Side.END] can be returned.
 * When the grid is horizontal only [Side.TOP] and [Side.BOTTOM] can be returned.
 *
 * @param itemView the [View] of the item at the given index.
 * @param layoutRightToLeft true if layout of the grid is right-to-left.
 * @return a set of [Side] containing:
 * - [Side.TOP] if the item touches the top side of the grid.
 * - [Side.BOTTOM] if the item touches the bottom side of the grid.
 * - [Side.START] if the item touches the start side of the grid.
 * - [Side.END] if the item touches the end side of the grid.
 */
internal fun StaggeredGridLayoutManager.sidesAdjacentToItem(itemView: View, layoutRightToLeft: Boolean): Sides {
    val layoutParams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
    val isFullSpan = layoutParams.isFullSpan
    val spanIndex = layoutParams.spanIndex
    val isAdjacentToGridTop: Boolean
    val isAdjacentToGridBottom: Boolean
    val isAdjacentToGridStart: Boolean
    val isAdjacentToGridEnd: Boolean
    if (layoutOrientation.isVertical) {
        val isAdjacentToGridLeft = spanIndex == 0
        val isAdjacentToGridRight = isFullSpan || spanIndex == spanCount - 1
        isAdjacentToGridStart = if (layoutRightToLeft) isAdjacentToGridRight else isAdjacentToGridLeft
        isAdjacentToGridEnd = if (layoutRightToLeft) isAdjacentToGridLeft else isAdjacentToGridRight
        isAdjacentToGridTop = false
        isAdjacentToGridBottom = false
    } else {
        isAdjacentToGridTop = spanIndex == 0
        isAdjacentToGridBottom = isFullSpan || spanIndex == spanCount - 1
        isAdjacentToGridStart = false
        isAdjacentToGridEnd = false
    }
    val sides = sidesOf()
    if (isAdjacentToGridTop) sides += Side.TOP
    if (isAdjacentToGridBottom) sides += Side.BOTTOM
    if (isAdjacentToGridStart) sides += Side.START
    if (isAdjacentToGridEnd) sides += Side.END
    return sides
}
