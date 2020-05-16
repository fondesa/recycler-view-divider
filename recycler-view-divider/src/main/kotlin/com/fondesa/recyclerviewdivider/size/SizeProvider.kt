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

package com.fondesa.recyclerviewdivider.size

import android.graphics.drawable.Drawable
import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Provides the divider's size.
 */
interface SizeProvider {

    /**
     * Gets the divider's size.
     * The size of an horizontal divider is its height.
     * The size of a vertical divider is its width.
     *
     * @param grid the [Grid] in which the divider is shown.
     * @param divider the [Divider] which will have the returned size.
     * @param dividerDrawable the divider's [Drawable].
     * @return the size in pixels of the given divider.
     */
    @Px
    fun getDividerSize(grid: Grid, divider: Divider, dividerDrawable: Drawable): Int
}
