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

package com.fondesa.recyclerviewdivider.size

import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.Orientation
import com.fondesa.recyclerviewdivider.Side
import com.fondesa.recyclerviewdivider.dividersAroundCell
import com.fondesa.recyclerviewdivider.test.allDividers
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.dummyGrid
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of [SizeProviderImpl].
 */
@RunWith(AndroidJUnit4::class)
class SizeProviderImplTest {

    @Test
    fun `getDividerSize - not null divider size - returns same size for each divider`() {
        val grid = dummyGrid(cellsInLines = intArrayOf(3, 4, 2))
        val provider = SizeProviderImpl(context = context, dividerSize = 23)

        grid.allDividers.forEach { divider ->
            @Px val actualSize = provider.getDividerSize(grid = grid, divider = divider, dividerDrawable = mock())

            assertEquals(23, actualSize)
        }
    }

    @Test
    fun `getDividerSize - null divider size, horizontal divider, drawable without intrinsic height - returns default size`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val divider = grid.dividersAroundCell(0).getValue(Side.TOP)
        val provider = SizeProviderImpl(context = context, dividerSize = null)
        val drawable = mock<Drawable> {
            on(it.intrinsicHeight) doReturn -1
            on(it.intrinsicWidth) doReturn -1
        }

        @Px val actualSize = provider.getDividerSize(grid = grid, divider = divider, dividerDrawable = drawable)

        assertEquals(context.getDefaultSize(), actualSize)
    }

    @Test
    fun `getDividerSize - null divider size, vertical divider, drawable without intrinsic width - returns default size`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val divider = grid.dividersAroundCell(0).getValue(Side.START)
        val provider = SizeProviderImpl(context = context, dividerSize = null)
        val drawable = mock<Drawable> {
            on(it.intrinsicHeight) doReturn -1
            on(it.intrinsicWidth) doReturn -1
        }

        @Px val actualSize = provider.getDividerSize(grid = grid, divider = divider, dividerDrawable = drawable)

        assertEquals(context.getDefaultSize(), actualSize)
    }

    @Test
    fun `getDividerSize - null divider size, horizontal divider, drawable with intrinsic height, returns intrinsic height`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val divider = grid.dividersAroundCell(0).getValue(Side.TOP)
        val provider = SizeProviderImpl(context = context, dividerSize = null)
        val drawable = mock<Drawable> {
            on(it.intrinsicHeight) doReturn 12
            on(it.intrinsicWidth) doReturn -1
        }

        @Px val actualSize = provider.getDividerSize(grid = grid, divider = divider, dividerDrawable = drawable)

        assertEquals(12, actualSize)
    }

    @Test
    fun `getDividerSize - null divider size, vertical divider, drawable with intrinsic width, returns intrinsic width`() {
        val grid = dummyGrid(orientation = Orientation.VERTICAL, cellsInLines = intArrayOf(3, 4, 2))
        val divider = grid.dividersAroundCell(0).getValue(Side.START)
        val provider = SizeProviderImpl(context = context, dividerSize = null)
        val drawable = mock<Drawable> {
            on(it.intrinsicHeight) doReturn -1
            on(it.intrinsicWidth) doReturn 23
        }

        @Px val actualSize = provider.getDividerSize(grid = grid, divider = divider, dividerDrawable = drawable)

        assertEquals(23, actualSize)
    }
}
