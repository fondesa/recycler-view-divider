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

@file:Suppress("OverridingDeprecatedMember", "DEPRECATION")

package com.fondesa.recyclerviewdivider.manager.tint

import androidx.annotation.ColorInt
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Defines a [TintManager] which doesn't depend on the item's position.
 */
@Deprecated("Use the new divider API instead.")
public abstract class FixedTintManager : TintManager {

    @ColorInt
    final override fun itemTint(groupCount: Int, groupIndex: Int): Int = itemTint()

    /**
     * @see TintManager.itemTint
     */
    @Deprecated("Use the new divider API instead.")
    @ColorInt
    public abstract fun itemTint(): Int
}

/**
 * Checks if the given [TintManager] is a [FixedTintManager], otherwise throws an exception.
 *
 * @return the [TintManager] casted to [FixedTintManager].
 * @throws IllegalArgumentException if [TintManager] isn't a [FixedTintManager].
 */
@Deprecated("Use the new divider API instead.")
internal fun TintManager.asFixed(): FixedTintManager {
    require(this is FixedTintManager) {
        "Only ${FixedTintManager::class.java.name} is supported with ${StaggeredGridLayoutManager::class.java.name}"
    }
    return this
}
