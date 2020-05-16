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

package com.fondesa.recyclerviewdivider.visibility

import com.fondesa.recyclerviewdivider.Orientation
import com.fondesa.recyclerviewdivider.test.allDividers
import com.fondesa.recyclerviewdivider.test.dummyGrid
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests of [VisibilityProviderImpl].
 */
class VisibilityProviderImplTest {

    @Test
    fun `isDividerVisible - first, last, side visible - returns true for each divider`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible)
        }
    }

    @Test
    fun `isDividerVisible - first, side visible, last not visible - returns true for each divider except last`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = true,
            isLastDividerVisible = false,
            areSideDividersVisible = true
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != divider.isLastDivider)
        }
    }

    @Test
    fun `isDividerVisible - last, side visible, first not visible - returns true for each divider except first`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != divider.isFirstDivider)
        }
    }

    @Test
    fun `isDividerVisible - first, last visible, side not visible - returns true for each divider except side`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = false
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != divider.isSideDivider)
        }
    }

    @Test
    fun `isDividerVisible - side visible, first, last not visible - returns true for each divider except first, last`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = false,
            isLastDividerVisible = false,
            areSideDividersVisible = true
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != (divider.isFirstDivider || divider.isLastDivider))
        }
    }

    @Test
    fun `isDividerVisible - first visible, last, side not visible - returns true for each divider except last, side`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = true,
            isLastDividerVisible = false,
            areSideDividersVisible = false
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != (divider.isSideDivider || divider.isLastDivider))
        }
    }

    @Test
    fun `isDividerVisible - last visible, first, side not visible - returns true for each divider except first, side`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = false
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != (divider.isSideDivider || divider.isFirstDivider))
        }
    }

    @Test
    fun `isDividerVisible - first, last, side not visible - returns true for each divider except for first, last, side`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val provider = VisibilityProviderImpl(
            isFirstDividerVisible = false,
            isLastDividerVisible = false,
            areSideDividersVisible = false
        )

        grid.allDividers.forEach { divider ->
            val isDividerVisible = provider.isDividerVisible(grid = grid, divider = divider)
            assertTrue(isDividerVisible != (divider.isSideDivider || divider.isLastDivider || divider.isFirstDivider))
        }
    }
}
