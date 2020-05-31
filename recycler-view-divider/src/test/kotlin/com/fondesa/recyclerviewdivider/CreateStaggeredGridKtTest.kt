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

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.staggeredLayoutManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of CreateStaggeredGrid.kt file.
 */
@RunWith(AndroidJUnit4::class)
class CreateStaggeredGridKtTest {

    @Test
    fun `staggeredGrid - returns grid with params got from layout manager`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.VERTICAL, reverseLayout = true)
        RecyclerView(context).layoutManager = layoutManager

        val grid = layoutManager.staggeredGrid()

        assertEquals(
            StaggeredGrid(
                spanCount = 2,
                orientation = Orientation.VERTICAL,
                layoutDirection = LayoutDirection(LayoutDirection.Horizontal.LEFT_TO_RIGHT, LayoutDirection.Vertical.BOTTOM_TO_TOP)
            ),
            grid
        )
    }
}
