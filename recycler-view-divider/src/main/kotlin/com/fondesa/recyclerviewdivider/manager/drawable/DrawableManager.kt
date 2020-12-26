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

@file:Suppress("DEPRECATION")

package com.fondesa.recyclerviewdivider.manager.drawable

import android.graphics.drawable.Drawable
import com.fondesa.recyclerviewdivider.RecyclerViewDivider

/**
 * Used to specify a custom logic to use different drawables as divider.
 * <br>
 * You can add a custom [DrawableManager] in your [RecyclerViewDivider.Builder] using
 * the [RecyclerViewDivider.Builder.drawableManager] method.
 */
@Deprecated("Use the new divider API instead.")
public interface DrawableManager {

    /**
     * Defines a custom Drawable for each group of divider.
     *
     * @param groupCount number of groups in a list (equal to the list size when the span count is 1).
     * @param groupIndex position of the group (equal to the item position when the span count is 1).
     * @return [Drawable] resource for the divider int the current position.
     */
    @Deprecated("Use the new divider API instead.")
    public fun itemDrawable(groupCount: Int, groupIndex: Int): Drawable
}
