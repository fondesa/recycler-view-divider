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

package com.fondesa.recyclerviewdivider.extensions

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by antoniolig on 11/10/17.
 */
internal val RecyclerView.LayoutManager.orientation: Int
    get() = when (this) {
        is LinearLayoutManager -> orientation
        is StaggeredGridLayoutManager -> orientation
        else -> RecyclerView.VERTICAL
    }

internal val RecyclerView.LayoutManager.spanCount: Int
    get() = when (this) {
        is GridLayoutManager -> spanCount
        is StaggeredGridLayoutManager -> spanCount
        else -> 1
    }

internal fun RecyclerView.LayoutManager.getSpanSize(itemPosition: Int): Int =
        (this as? GridLayoutManager)?.spanSizeLookup?.getSpanSize(itemPosition) ?: 1

internal fun RecyclerView.LayoutManager.getGroupIndex(itemPosition: Int): Int =
        (this as? GridLayoutManager)?.spanSizeLookup?.getSpanGroupIndex(itemPosition, spanCount) ?:
                itemPosition

internal fun RecyclerView.LayoutManager.getGroupCount(itemCount: Int): Int {
    return if (this is GridLayoutManager) {
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
}

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
