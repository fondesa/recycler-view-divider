/*
 * Copyright (c) 2021 Giorgio Antonioli
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

package com.fondesa.recyclerviewdivider.cache

import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.Grid

/**
 * Caches the [Grid] used to calculate the offsets and draw dividers.
 */
internal interface GridCache {
    /**
     * Gets the [Grid] from the cache, if any.
     *
     * @param spanCount the span count of the [RecyclerView] which shows the dividers.
     * @param itemCount the number of items of the [RecyclerView] which shows the dividers.
     * @return the grid in cache, or null if the there isn't a valid [Grid] in cache for the given parameters.
     */
    fun get(spanCount: Int, itemCount: Int): Grid?

    /**
     * Saves the [Grid] into the cache.
     *
     * @param spanCount the span count of the [RecyclerView] which shows the dividers.
     * @param itemCount the number of items of the [RecyclerView] which shows the dividers.
     * @param grid the [Grid] which should be saved.
     */
    fun put(spanCount: Int, itemCount: Int, grid: Grid)

    /**
     * Clears the cache.
     */
    fun clear()
}
