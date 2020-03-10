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

package com.fondesa.recyclerviewdivider.manager.drawable

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Defines a [DrawableManager] which doesn't depend on the item's position.
 */
abstract class FixedDrawableManager : DrawableManager {

    final override fun itemDrawable(groupCount: Int, groupIndex: Int): Drawable = itemDrawable()

    /**
     * @see DrawableManager.itemDrawable
     */
    abstract fun itemDrawable(): Drawable
}

/**
 * Checks if the given [DrawableManager] is a [FixedDrawableManager], otherwise throws an exception.
 *
 * @return the [DrawableManager] casted to [FixedDrawableManager].
 * @throws IllegalArgumentException if [DrawableManager] isn't a [FixedDrawableManager].
 */
internal fun DrawableManager.asFixed(): FixedDrawableManager {
    require(this is FixedDrawableManager) {
        "Only ${FixedDrawableManager::class.java.name} is supported with ${StaggeredGridLayoutManager::class.java.name}"
    }
    return this
}