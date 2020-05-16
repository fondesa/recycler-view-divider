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

package com.fondesa.recyclerviewdivider.drawable

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Provides the divider's [Drawable].
 */
interface DrawableProvider {

    /**
     * Gets the [Drawable] of the given divider.
     * If the divider simply needs a color, you can return a [ColorDrawable] of the wanted color.
     *
     * @param grid the [Grid] in which the divider is shown.
     * @param divider the [Divider] which will render the returned [Drawable].
     * @return the [Drawable] of the given divider.
     */
    fun getDividerDrawable(grid: Grid, divider: Divider): Drawable
}
