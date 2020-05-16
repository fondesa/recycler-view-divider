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

import android.graphics.drawable.Drawable
import androidx.annotation.VisibleForTesting
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Implementation of [DrawableProvider] which provides the same [Drawable] for each divider.
 *
 * @param drawable the [Drawable] of each divider.
 */
internal class DrawableProviderImpl(@VisibleForTesting internal val drawable: Drawable) : DrawableProvider {

    override fun getDividerDrawable(grid: Grid, divider: Divider): Drawable = drawable
}
