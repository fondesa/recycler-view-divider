/*
 * Copyright (c) 2020 Fondesa
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

package com.fondesa.recyclerviewdivider.manager.size

import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.RecyclerViewDivider

/**
 * Used to specify a custom logic to set different sizes to the divider.
 * <br>
 * Size is referred to the height of an horizontal divider and to the width of a vertical divider.
 * <br>
 * You can add a custom [SizeManager] in your [RecyclerViewDivider.Builder] using
 * the [RecyclerViewDivider.Builder.sizeManager] method.
 */
interface SizeManager {

    /**
     * Defines a custom size for each group of divider.
     *
     * @param drawable current divider's [Drawable].
     * @param orientation [RecyclerView.VERTICAL] or [RecyclerView.HORIZONTAL].
     * @param groupCount number of groups in a list (equal to the list size when the span count is 1).
     * @param groupIndex position of the group (equal to the item position when the span count is 1).
     * @return height for an horizontal divider, width for a vertical divider (expressed in pixels).
     */
    @Px
    fun itemSize(drawable: Drawable, orientation: Int, groupCount: Int, groupIndex: Int): Int
}
