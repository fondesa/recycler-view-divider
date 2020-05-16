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

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.linearLayoutManager
import com.fondesa.recyclerviewdivider.test.rtl
import com.fondesa.recyclerviewdivider.test.staggeredLayoutManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of ObtainLayoutDirection.kt file.
 */
@RunWith(AndroidJUnit4::class)
class ObtainLayoutDirectionKtTest {

    @Test
    fun `obtainLayoutDirection - vertical LTR LinearLayoutManager - returns top-to-bottom, left-to-right`() {
        val layoutManager = linearLayoutManager(Orientation.VERTICAL, false).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical reversed LTR LinearLayoutManager - returns bottom-to-top, left-to-right`() {
        val layoutManager = linearLayoutManager(Orientation.VERTICAL, true).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.BOTTOM_TO_TOP
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical RTL LinearLayoutManager - returns top-to-bottom, right-to-left`() {
        val layoutManager = linearLayoutManager(Orientation.VERTICAL, false).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                } else {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                },
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical reversed RTL LinearLayoutManager - returns bottom-to-top, right-to-left`() {
        val layoutManager = linearLayoutManager(Orientation.VERTICAL, true).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                } else {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                },
                vertical = LayoutDirection.Vertical.BOTTOM_TO_TOP
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - horizontal LTR LinearLayoutManager - returns top-to-bottom, left-to-right`() {
        val layoutManager = linearLayoutManager(Orientation.HORIZONTAL, false).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - horizontal RTL LinearLayoutManager - returns top-to-bottom, right-to-left`() {
        val layoutManager = linearLayoutManager(Orientation.HORIZONTAL, false).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                } else {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                },
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - reversed horizontal LTR LinearLayoutManager - returns top-to-bottom, right-to-left`() {
        val layoutManager = linearLayoutManager(Orientation.HORIZONTAL, true).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.RIGHT_TO_LEFT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - reversed horizontal RTL LinearLayoutManager - returns top-to-bottom, left-to-right`() {
        val layoutManager = linearLayoutManager(Orientation.HORIZONTAL, true).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                } else {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                },
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical LTR StaggeredGridLayoutManager - returns top-to-bottom, left-to-right`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.VERTICAL, false).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical reversed LTR StaggeredGridLayoutManager - returns bottom-to-top, left-to-right`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.VERTICAL, true).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.BOTTOM_TO_TOP
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical RTL StaggeredGridLayoutManager - returns top-to-bottom, right-to-left`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.VERTICAL, false).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                } else {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                },
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - vertical reversed RTL StaggeredGridLayoutManager - returns bottom-to-top, right-to-left`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.VERTICAL, true).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                } else {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                },
                vertical = LayoutDirection.Vertical.BOTTOM_TO_TOP
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - horizontal LTR StaggeredGridLayoutManager - returns top-to-bottom, left-to-right`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.HORIZONTAL, false).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.LEFT_TO_RIGHT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - horizontal RTL StaggeredGridLayoutManager - returns top-to-bottom, right-to-left`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.HORIZONTAL, false).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                } else {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                },
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - reversed horizontal LTR StaggeredGridLayoutManager - returns top-to-bottom, right-to-left`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.HORIZONTAL, true).also {
            RecyclerView(context).layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = LayoutDirection.Horizontal.RIGHT_TO_LEFT,
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }

    @Test
    fun `obtainLayoutDirection - reversed horizontal RTL StaggeredGridLayoutManager - returns top-to-bottom, left-to-right`() {
        val layoutManager = staggeredLayoutManager(2, Orientation.HORIZONTAL, true).also {
            RecyclerView(context).rtl().layoutManager = it
        }

        assertEquals(
            LayoutDirection(
                horizontal = if (Build.VERSION.SDK_INT >= 17) {
                    LayoutDirection.Horizontal.LEFT_TO_RIGHT
                } else {
                    LayoutDirection.Horizontal.RIGHT_TO_LEFT
                },
                vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM
            ),
            layoutManager.obtainLayoutDirection()
        )
    }
}
