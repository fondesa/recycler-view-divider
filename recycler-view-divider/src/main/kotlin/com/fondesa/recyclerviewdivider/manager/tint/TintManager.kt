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

package com.fondesa.recyclerviewdivider.manager.tint

import androidx.annotation.ColorInt
import com.fondesa.recyclerviewdivider.RecyclerViewDivider

/**
 * Used to specify a custom logic to use different tint colors to tint divider's drawables.
 * <br>
 * You can add a custom [TintManager] in your [RecyclerViewDivider.Builder] using
 * the [RecyclerViewDivider.Builder.tintManager] method.
 */
interface TintManager {

    /**
     * Defines a custom tint color for each group of divider.
     *
     * @param groupCount number of groups in a list (equal to the list size when the span count is 1).
     * @param groupIndex position of the group (equal to the item position when the span count is 1).
     * @return tint color for the divider's drawable in the current position.
     */
    @ColorInt
    fun itemTint(groupCount: Int, groupIndex: Int): Int
}
