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

import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Identifies the grid layout of a [StaggeredGridLayoutManager].
 * A vertical grid is a grid shown in a layout manager with vertical orientation.
 * An horizontal grid is a grid shown in a layout manager with horizontal orientation.
 *
 * @param spanCount the number of possible columns in a vertical grid or the number of possible rows in an horizontal grid.
 * @param orientation [Orientation.VERTICAL] if the grid is vertical, [Orientation.HORIZONTAL] if the grid is horizontal.
 * @param layoutDirection identifies if the grid is top-to-bottom, right-to-left, bottom-to-top, left-to-right.
 */
internal data class StaggeredGrid(
    val spanCount: Int,
    val orientation: Orientation,
    val layoutDirection: LayoutDirection
)

/**
 * Identifies the cell inside a [StaggeredGrid].
 *
 * @param spanIndex the index of the span inside a line of the staggered grid.
 * @param isFullSpan true if the cell takes the whole space of a line.
 */
internal data class StaggeredCell(val spanIndex: Int, val isFullSpan: Boolean)
