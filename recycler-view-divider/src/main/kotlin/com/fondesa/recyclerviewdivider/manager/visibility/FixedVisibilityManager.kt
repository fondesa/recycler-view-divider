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

package com.fondesa.recyclerviewdivider.manager.visibility

import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Defines a [VisibilityManager] which doesn't depend on the item's position.
 */
@Deprecated("Use the new divider API instead.")
public abstract class FixedVisibilityManager : VisibilityManager {

    final override fun itemVisibility(
        groupCount: Int,
        groupIndex: Int
    ): VisibilityManager.VisibilityType = itemVisibility()

    /**
     * @see VisibilityManager.itemVisibility
     */
    @Deprecated("Use the new divider API instead.")
    public abstract fun itemVisibility(): VisibilityManager.VisibilityType
}

/**
 * Checks if the given [VisibilityManager] is a [FixedVisibilityManager], otherwise throws an exception.
 *
 * @return the [VisibilityManager] casted to [FixedVisibilityManager].
 * @throws IllegalArgumentException if [VisibilityManager] isn't a [FixedVisibilityManager].
 */
@Deprecated("Use the new divider API instead.")
internal fun VisibilityManager.asFixed(): FixedVisibilityManager {
    require(this is FixedVisibilityManager) {
        "Only ${FixedVisibilityManager::class.java.name} is supported with ${StaggeredGridLayoutManager::class.java.name}"
    }
    return this
}
