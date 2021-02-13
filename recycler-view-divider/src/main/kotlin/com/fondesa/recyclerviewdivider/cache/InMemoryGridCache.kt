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

import com.fondesa.recyclerviewdivider.Grid

/**
 * Implementation of [GridCache] which stores the grid in memory.
 */
internal class InMemoryGridCache : GridCache {
    private var cacheEntry: CacheEntry? = null

    override fun get(spanCount: Int, itemCount: Int): Grid? {
        val cacheEntry = cacheEntry ?: return null
        val isCacheValid = cacheEntry.spanCount == spanCount && cacheEntry.itemCount == itemCount
        return cacheEntry.grid.takeIf { isCacheValid }
    }

    override fun put(spanCount: Int, itemCount: Int, grid: Grid) {
        cacheEntry = CacheEntry(
            spanCount = spanCount,
            itemCount = itemCount,
            grid = grid
        )
    }

    override fun clear() {
        cacheEntry = null
    }

    private data class CacheEntry(
        val spanCount: Int,
        val itemCount: Int,
        val grid: Grid
    )
}
