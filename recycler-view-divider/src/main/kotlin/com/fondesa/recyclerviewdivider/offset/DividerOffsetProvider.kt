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
 * Provides the divider's offset.
 */
public fun interface DividerOffsetProvider {

    /**
     * Gets the divider's offset.
     * Useful to balance the size of the items in a grid with multiple columns/rows.
     *
     * @param grid the [Grid] in which the divider is shown.
     * @param divider the [Divider] which needs to establish its offset.
     * @param dividerSide the side of the cell on which the divider is shown.
     * @param size the size of the divider.
     * @return the offset which should be rendered by the given divider.
     */
    @Px
    public fun getOffsetFromSize(grid: Grid, divider: Divider, dividerSide: Side, @Px size: Int): Int
}
