/*
 * Copyright (c) 2017 Fondesa
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

package com.fondesa.recyclerviewdivider.extension

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Get the orientation of a [RecyclerView] using its layout manager.
 *
 * @return [RecyclerView.VERTICAL] or [RecyclerView.HORIZONTAL]
 */
internal val RecyclerView.LayoutManager.orientation: Int
    get() = when (this) {
        is LinearLayoutManager -> orientation
        is StaggeredGridLayoutManager -> orientation
        else -> RecyclerView.VERTICAL
    }

/**
 * Get the span count of a [RecyclerView].
 * <br>
 * If the layout manager hasn't a span count (like [LinearLayoutManager]), the span count will be 1.
 *
 * @return span count of the [RecyclerView].
 */
internal val RecyclerView.LayoutManager.spanCount: Int
    get() = when (this) {
        is GridLayoutManager -> spanCount
        is StaggeredGridLayoutManager -> spanCount
        else -> 1
    }

/**
 * Check the span size of the current item.
 * <br>
 * The span size will be minor than or equal to the span count.
 *
 * @param itemPosition position of the current item.
 * @return span size of the current item
 */
internal fun RecyclerView.LayoutManager.getSpanSize(itemPosition: Int): Int =
        (this as? GridLayoutManager)?.spanSizeLookup?.getSpanSize(itemPosition) ?: 1

/**
 * Calculate the group in which the item is.
 * <br>
 * This value is between 0 and [getGroupCount] - 1.
 *
 * @param itemPosition position of the current item.
 * @return the index of the group.
 */
internal fun RecyclerView.LayoutManager.getGroupIndex(itemPosition: Int): Int =
        (this as? GridLayoutManager)?.spanSizeLookup?.getSpanGroupIndex(itemPosition, spanCount) ?:
                itemPosition

/**
 * Calculate the number of items' group in a list.
 * <br>
 * If the span count is 1 (for example when the layout manager is a [LinearLayoutManager]),
 * the group count will be equal to the span count.
 *
 * @param itemCount number of items in the list.
 * @return the number of groups
 */
internal fun RecyclerView.LayoutManager.getGroupCount(itemCount: Int): Int = if (this is GridLayoutManager) {
    val spanSizeLookup = spanSizeLookup
    val spanCount = spanCount

    var groupCount = 0
    var pos = 0
    while (pos < itemCount) {
        if (spanSizeLookup.getSpanIndex(pos, spanCount) == 0) {
            groupCount++
        }
        pos++
    }
    groupCount
} else itemCount

/**
 * Calculate the span accumulated in this line.
 * <br>
 * This span is calculated through the sum of the previous items' spans
 * in this line and the current item's span.
 *
 * @param spanSize     span size of the item.
 * @param itemPosition position of the current item.
 * @param groupIndex   current index of the group.
 * @return accumulated span.
 */
internal fun RecyclerView.LayoutManager.getAccumulatedSpanInLine(spanSize: Int, itemPosition: Int, groupIndex: Int): Int {
    var lineAccumulatedSpan = spanSize
    var tempPos: Int = itemPosition - 1
    while (tempPos >= 0) {
        val tempGroupIndex = getGroupIndex(tempPos)
        if (tempGroupIndex == groupIndex) {
            val tempSpanSize = getSpanSize(tempPos)
            lineAccumulatedSpan += tempSpanSize
        } else {
            // if the group index change, it means that line is changed
            break
        }
        tempPos--
    }
    return lineAccumulatedSpan
}
