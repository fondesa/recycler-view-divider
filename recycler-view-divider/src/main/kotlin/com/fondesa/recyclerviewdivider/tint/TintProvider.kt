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

package com.fondesa.recyclerviewdivider.tint

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Provides the tint color which will tint the divider's [Drawable].
 */
public fun interface TintProvider {

    /**
     * Gets the tint color of the given divider's [Drawable].
     *
     * @param grid the [Grid] in which the divider is shown.
     * @param divider the [Divider] which will tint its [Drawable].
     * @return the tint color of the given divider or null if the divider shouldn't be tinted.
     */
    @ColorInt
    public fun getDividerTintColor(grid: Grid, divider: Divider): Int?
}
