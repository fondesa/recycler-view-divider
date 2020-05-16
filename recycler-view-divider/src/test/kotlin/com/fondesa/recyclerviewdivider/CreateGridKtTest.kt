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

package com.fondesa.recyclerviewdivider

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.gridLayoutManager
import com.fondesa.recyclerviewdivider.test.linearLayoutManager
import com.fondesa.recyclerviewdivider.test.rtl
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Tests of CreateGrid.kt file.
 */
@RunWith(AndroidJUnit4::class)
class CreateGridKtTest {

    @Test
    fun `grid - vertical LinearLayoutManager`() {
        val layoutManager = linearLayoutManager(Orientation.VERTICAL, false)
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 1,
            orientation = Orientation.VERTICAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(4)

        assertEquals(expectedGrid, actualGrid)
    }

    @Config(minSdk = 17)
    @Test
    fun `grid - horizontal RTL LinearLayoutManager`() {
        val layoutManager = linearLayoutManager(Orientation.HORIZONTAL, false)
        RecyclerView(context).rtl().layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 1,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.RIGHT_TO_LEFT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(4)

        assertEquals(expectedGrid, actualGrid)
    }

    @Test
    fun `grid - vertical GridLayoutManager, 1 column`() {
        val layoutManager = gridLayoutManager(1, Orientation.VERTICAL, false)
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 1,
            orientation = Orientation.VERTICAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(4)

        assertEquals(expectedGrid, actualGrid)
    }

    @Test
    fun `grid - horizontal reversed GridLayoutManager, 1 row`() {
        val layoutManager = gridLayoutManager(1, Orientation.HORIZONTAL, true)
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 1,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.RIGHT_TO_LEFT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(4)

        assertEquals(expectedGrid, actualGrid)
    }

    @Test
    fun `grid - vertical reversed GridLayoutManager, multiple equal columns`() {
        val layoutManager = gridLayoutManager(2, Orientation.VERTICAL, true)
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 2,
            orientation = Orientation.VERTICAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.BOTTOM_TO_TOP
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(8)

        assertEquals(expectedGrid, actualGrid)
    }

    @Test
    fun `grid - horizontal GridLayoutManager, multiple equal columns`() {
        val layoutManager = gridLayoutManager(3, Orientation.HORIZONTAL, false)
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 3,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1), Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(12)

        assertEquals(expectedGrid, actualGrid)
    }

    @Test
    fun `grid - vertical GridLayoutManager, multiple columns with different spans`() {
        val layoutManager = gridLayoutManager(3, Orientation.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0, 3, 7 -> 2
                else -> 1
            }
        }
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 3,
            orientation = Orientation.VERTICAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 2), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 2))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 2)))
            )
        )

        val actualGrid = layoutManager.grid(8)

        assertEquals(expectedGrid, actualGrid)
    }

    @Test
    fun `grid - horizontal GridLayoutManager, multiple columns with different spans`() {
        val layoutManager = gridLayoutManager(3, Orientation.HORIZONTAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                3, 6 -> 3
                5 -> 2
                else -> 1
            }
        }
        RecyclerView(context).layoutManager = layoutManager
        val expectedGrid = Grid(
            spanCount = 3,
            orientation = Orientation.HORIZONTAL,
            layoutDirection = LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            lines = listOf(
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 1), Cell(spanSize = 1))),
                Line(cells = listOf(Cell(spanSize = 3))),
                Line(cells = listOf(Cell(spanSize = 1), Cell(spanSize = 2))),
                Line(cells = listOf(Cell(spanSize = 3))),
                Line(cells = listOf(Cell(spanSize = 1)))
            )
        )

        val actualGrid = layoutManager.grid(8)

        assertEquals(expectedGrid, actualGrid)
    }
}
