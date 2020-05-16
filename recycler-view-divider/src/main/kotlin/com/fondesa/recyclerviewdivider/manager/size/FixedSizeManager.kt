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

@file:Suppress("DEPRECATION", "OverridingDeprecatedMember")

package com.fondesa.recyclerviewdivider.manager.size

import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Defines a [SizeManager] which doesn't depend on the item's position.
 */
@Deprecated("Use the new divider API instead.")
abstract class FixedSizeManager : SizeManager {

    @Px
    final override fun itemSize(
        drawable: Drawable,
        orientation: Int,
        groupCount: Int,
        groupIndex: Int
    ): Int = itemSize(drawable, orientation)

    /**
     * @see SizeManager.itemSize
     */
    @Deprecated("Use the new divider API instead.")
    @Px
    abstract fun itemSize(drawable: Drawable, orientation: Int): Int
}

/**
 * Checks if the given [SizeManager] is a [FixedSizeManager], otherwise throws an exception.
 *
 * @return the [SizeManager] casted to [FixedSizeManager].
 * @throws IllegalArgumentException if [SizeManager] isn't a [FixedSizeManager].
 */
@Deprecated("Use the new divider API instead.")
internal fun SizeManager.asFixed(): FixedSizeManager {
    require(this is FixedSizeManager) {
        "Only ${FixedSizeManager::class.java.name} is supported with ${StaggeredGridLayoutManager::class.java.name}"
    }
    return this
}
