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

package com.fondesa.recyclerviewdivider.inset

import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.test.allDividers
import com.fondesa.recyclerviewdivider.test.dummyGrid
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of [InsetProviderImpl].
 */
class InsetProviderImplTest {

    @Test
    fun `getDividerInsetStart, getDividerInsetEnd - return same insets for each divider`() {
        val grid = dummyGrid(cellsInLines = intArrayOf(3, 4, 2))
        val provider = InsetProviderImpl(dividerInsetStart = 56, dividerInsetEnd = 23)

        grid.allDividers.forEach { divider ->
            @Px val actualInsetStart = provider.getDividerInsetStart(grid = grid, divider = divider)
            @Px val actualInsetEnd = provider.getDividerInsetEnd(grid = grid, divider = divider)

            assertEquals(56, actualInsetStart)
            assertEquals(23, actualInsetEnd)
        }
    }
}
