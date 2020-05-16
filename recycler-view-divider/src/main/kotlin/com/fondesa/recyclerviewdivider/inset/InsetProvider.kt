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

package com.fondesa.recyclerviewdivider.inset

import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Provides the divider's insets.
 */
interface InsetProvider {

    /**
     * Gets the divider's start inset.
     * The start inset in an horizontal divider is the left inset in left-to-right and the right inset in right-to-left.
     * The start inset in a vertical divider is the top inset in top-to-bottom and the bottom inset in bottom-to-top.
     *
     * @param grid the [Grid] in which the divider is shown.
     * @param divider the [Divider] which will have the returned start inset.
     * @return the start inset size in pixels of the given divider.
     */
    @Px
    fun getDividerInsetStart(grid: Grid, divider: Divider): Int

    /**
     * Gets the divider's end inset.
     * The end inset in an horizontal divider is the right inset in left-to-right and the left inset in right-to-left.
     * The end inset in a vertical divider is the bottom inset in top-to-bottom and the top inset in bottom-to-top.
     *
     * @param grid the [Grid] in which the divider is shown.
     * @param divider the [Divider] which will have the returned end inset.
     * @return the end inset size in pixels of the given divider.
     */
    @Px
    fun getDividerInsetEnd(grid: Grid, divider: Divider): Int
}
