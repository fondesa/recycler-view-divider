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

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Identifies the direction of a [RecyclerView]'s layout.
 *
 * @param horizontal the horizontal direction of the [RecyclerView].
 * @param vertical the vertical direction of the [RecyclerView].
 */
public data class LayoutDirection(
    val horizontal: Horizontal,
    val vertical: Vertical
) {

    /**
     * @return true if the horizontal layout direction is right-to-left.
     */
    val isRightToLeft: Boolean get() = horizontal == Horizontal.RIGHT_TO_LEFT

    /**
     * @return true if the vertical layout direction is bottom-to-top.
     */
    val isBottomToTop: Boolean get() = vertical == Vertical.BOTTOM_TO_TOP

    /**
     * Identifies the horizontal direction of a [RecyclerView].
     */
    public enum class Horizontal {

        /**
         * The first items are on the left.
         * The default.
         */
        LEFT_TO_RIGHT,

        /**
         * The first items are on the right.
         * When the layout is RTL or its layout manager can reverse the layout calculations (e.g. [LinearLayoutManager.getReverseLayout]).
         */
        RIGHT_TO_LEFT
    }

    /**
     * Identifies the vertical direction of a [RecyclerView].
     */
    public enum class Vertical {

        /**
         * The first items are on the top.
         * The default.
         */
        TOP_TO_BOTTOM,

        /**
         * The first items are on the bottom.
         * When the layout manager can reverse the layout calculations (e.g. [LinearLayoutManager.getReverseLayout]).
         */
        BOTTOM_TO_TOP
    }
}
