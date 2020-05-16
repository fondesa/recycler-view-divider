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

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @return the [LayoutDirection] of the given [LinearLayoutManager].
 */
internal fun LinearLayoutManager.obtainLayoutDirection(): LayoutDirection = obtainLayoutDirection(orientation, reverseLayout)

/**
 * @return the [LayoutDirection] of the given [StaggeredGridLayoutManager].
 */
internal fun StaggeredGridLayoutManager.obtainLayoutDirection(): LayoutDirection = obtainLayoutDirection(orientation, reverseLayout)

private fun RecyclerView.LayoutManager.obtainLayoutDirection(orientation: Int, isReversed: Boolean): LayoutDirection {
    val isVertical = orientation == RecyclerView.VERTICAL
    val shouldDrawFromRightToLeft = if (isVertical) isRTL else isRTL xor isReversed
    val horizontalDrawDirection = if (shouldDrawFromRightToLeft) {
        LayoutDirection.Horizontal.RIGHT_TO_LEFT
    } else {
        LayoutDirection.Horizontal.LEFT_TO_RIGHT
    }
    val verticalDrawDirection = if (isVertical && isReversed) {
        LayoutDirection.Vertical.BOTTOM_TO_TOP
    } else {
        LayoutDirection.Vertical.TOP_TO_BOTTOM
    }
    return LayoutDirection(horizontal = horizontalDrawDirection, vertical = verticalDrawDirection)
}

private val RecyclerView.LayoutManager.isRTL: Boolean get() = layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL
