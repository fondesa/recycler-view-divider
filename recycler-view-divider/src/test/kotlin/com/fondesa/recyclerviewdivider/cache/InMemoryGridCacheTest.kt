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

import com.fondesa.recyclerviewdivider.test.dummyGrid
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Tests of [InMemoryGridCache].
 */
class InMemoryGridCacheTest {
    private val cache = InMemoryGridCache()

    @Test
    fun `put, get - same item count and span count`() {
        val grid = dummyGrid()

        cache.put(4, 5, grid)

        assertEquals(grid, cache.get(4, 5))
        assertEquals(grid, cache.get(4, 5))
    }

    @Test
    fun `put, get - different item count or span count`() {
        val grid = dummyGrid()

        cache.put(4, 5, grid)

        assertNull(cache.get(4, 3))
        assertNull(cache.get(3, 5))
        assertNull(cache.get(3, 3))
    }

    @Test
    fun `put, get, clear - no grid in cache`() {
        val grid = dummyGrid()

        cache.put(4, 5, grid)
        cache.clear()

        assertNull(cache.get(4, 5))
    }
}
