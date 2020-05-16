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

package com.fondesa.recyclerviewdivider.tint

import android.graphics.Color
import androidx.annotation.ColorInt
import com.fondesa.recyclerviewdivider.test.allDividers
import com.fondesa.recyclerviewdivider.test.dummyGrid
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Tests of [TintProviderImpl].
 */
class TintProviderImplTest {

    @Test
    fun `getDividerTint - null tint color - returns null for each divider`() {
        val grid = dummyGrid(cellsInLines = intArrayOf(3, 4, 2))
        val provider = TintProviderImpl(dividerTintColor = null)

        grid.allDividers.forEach { divider ->
            @ColorInt val actualTintColor = provider.getDividerTintColor(grid = grid, divider = divider)

            assertNull(actualTintColor)
        }
    }

    @Test
    fun `getDividerTint - not null tint color - returns same color for each divider`() {
        val grid = dummyGrid(cellsInLines = intArrayOf(3, 4, 2))
        val provider = TintProviderImpl(dividerTintColor = Color.RED)

        grid.allDividers.forEach { divider ->
            @ColorInt val actualTintColor = provider.getDividerTintColor(grid = grid, divider = divider)

            assertNotNull(actualTintColor)
            assertEquals(Color.RED, actualTintColor)
        }
    }
}
