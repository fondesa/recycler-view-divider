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

package com.fondesa.recyclerviewdivider.manager.inset

import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.fondesa.recyclerviewdivider.manager.drawable.DrawableManager

/**
 * Used to specify a custom logic to set different insets to each divider.
 * <br>
 * You can add a custom [DrawableManager] in your [RecyclerViewDivider.Builder] using
 * the [RecyclerViewDivider.Builder.drawableManager] method.
 */
interface InsetManager {

    /**
     * Defines a custom inset that will be applied on the top of each element for a vertical list and
     * on the left of each element for an horizontal list.
     *
     * @param groupCount number of groups in a list (equal to the list size when the span count is 1).
     * @param groupIndex position of the group (equal to the item position when the span count is 1).
     * @return inset before the item (expressed in pixels).
     */
    @Px
    fun itemInsetBefore(groupCount: Int, groupIndex: Int): Int

    /**
     * Defines a custom inset that will be applied on the bottom of each element for a vertical list and
     * on the right of each element for an horizontal list.
     *
     * @param groupCount number of groups in a list (equal to the list size when the span count is 1).
     * @param groupIndex position of the group (equal to the item position when the span count is 1).
     * @return inset after the item (expressed in pixels).
     */
    @Px
    fun itemInsetAfter(groupCount: Int, groupIndex: Int): Int
}
