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

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.assertEquals
import com.fondesa.recyclerviewdivider.test.bounds
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.customLayoutManager
import com.fondesa.recyclerviewdivider.test.getItemOffsets
import com.fondesa.recyclerviewdivider.test.linearLayoutManager
import com.fondesa.recyclerviewdivider.test.margins
import com.fondesa.recyclerviewdivider.test.onDraw
import com.fondesa.recyclerviewdivider.test.staggeredLayoutManager
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Tests of [StaggeredDividerItemDecoration].
 */
@RunWith(AndroidJUnit4::class)
class StaggeredDividerItemDecorationTest {

    @Test(expected = IllegalLayoutManagerException::class)
    fun `getItemOffsets - LinearLayoutManager - IllegalLayoutManagerException thrown`() {
        decoration().getItemOffsets(linearLayoutManager(), false, View(context))
    }

    @Test(expected = IllegalLayoutManagerException::class)
    fun `getItemOffsets - custom layout manager - IllegalLayoutManagerException thrown`() {
        decoration().getItemOffsets(customLayoutManager(), false, View(context))
    }

    @Test
    fun `getItemOffsets - vertical staggered, 1 column`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 0, 10, 10),
            Rect(10, 0, 10, 10),
            Rect(10, 0, 10, 10)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 0, 0, 7),
            Rect(0, 0, 0, 7),
            Rect(0, 0, 0, 7)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 1)
        )
    }

    @Test
    fun `getItemOffsets - vertical reversed staggered, 1 column`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 10, 10, 0),
            Rect(10, 10, 10, 0),
            Rect(10, 10, 10, 0)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 7, 0, 0),
            Rect(0, 7, 0, 0),
            Rect(0, 7, 0, 0)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(1, 1, 1, 0)
        )
    }

    @Test
    fun `getItemOffsets - horizontal staggered, 1 row`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 10, 10, 10),
            Rect(0, 10, 10, 10),
            Rect(0, 10, 10, 10)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 0, 7, 0),
            Rect(0, 0, 7, 0),
            Rect(0, 0, 7, 0)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL staggered, 1 row`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 10, 0, 10),
            Rect(10, 10, 0, 10),
            Rect(10, 10, 0, 10)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(7, 0, 0, 0),
            Rect(7, 0, 0, 0),
            Rect(7, 0, 0, 0)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1)
        )
    }

    @Test
    fun `getItemOffsets - horizontal reversed staggered, 1 row`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 10, 0, 10),
            Rect(10, 10, 0, 10),
            Rect(10, 10, 0, 10)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(7, 0, 0, 0),
            Rect(7, 0, 0, 0),
            Rect(7, 0, 0, 0)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL reversed staggered, 1 row`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 10, 10, 10),
            Rect(0, 10, 10, 10),
            Rect(0, 10, 10, 10)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 0, 7, 0),
            Rect(0, 0, 7, 0),
            Rect(0, 0, 7, 0)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(0, false),
            view(0, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1)
        )
    }

    @Test
    fun `getItemOffsets - vertical staggered, multiple columns`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 0, 5, 10),
            Rect(5, 0, 10, 10),
            Rect(10, 0, 10, 10),
            Rect(10, 0, 5, 10),
            Rect(5, 0, 10, 10),
            Rect(10, 0, 5, 10)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 0, 4, 7),
            Rect(3, 0, 0, 7),
            Rect(0, 0, 0, 7),
            Rect(0, 0, 4, 7),
            Rect(3, 0, 0, 7),
            Rect(0, 0, 4, 7)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 0, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(1, 0, 1, 1)
        )
    }

    @Test
    fun `getItemOffsets - vertical reversed staggered, multiple columns`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 10, 5, 0),
            Rect(5, 10, 10, 0),
            Rect(10, 10, 10, 0),
            Rect(10, 10, 5, 0),
            Rect(5, 10, 10, 0),
            Rect(10, 10, 5, 0)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 7, 4, 0),
            Rect(3, 7, 0, 0),
            Rect(0, 7, 0, 0),
            Rect(0, 7, 4, 0),
            Rect(3, 7, 0, 0),
            Rect(0, 7, 4, 0)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 1, 1, 0),
            Rect(0, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(0, 1, 1, 0),
            Rect(1, 1, 1, 0)
        )
    }

    @Test
    fun `getItemOffsets - horizontal staggered, multiple rows`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 10, 10, 5),
            Rect(0, 5, 10, 10),
            Rect(0, 10, 10, 10),
            Rect(0, 10, 10, 5),
            Rect(0, 5, 10, 10),
            Rect(0, 10, 10, 5)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 0, 7, 4),
            Rect(0, 3, 7, 0),
            Rect(0, 0, 7, 0),
            Rect(0, 0, 7, 4),
            Rect(0, 3, 7, 0),
            Rect(0, 0, 7, 4)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL staggered - multiple rows`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 10, 0, 5),
            Rect(10, 5, 0, 10),
            Rect(10, 10, 0, 10),
            Rect(10, 10, 0, 5),
            Rect(10, 5, 0, 10),
            Rect(10, 10, 0, 5)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(7, 0, 0, 4),
            Rect(7, 3, 0, 0),
            Rect(7, 0, 0, 0),
            Rect(7, 0, 0, 4),
            Rect(7, 3, 0, 0),
            Rect(7, 0, 0, 4)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1)
        )
    }

    @Test
    fun `getItemOffsets - horizontal reversed staggered, multiple rows`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(10, 10, 0, 5),
            Rect(10, 5, 0, 10),
            Rect(10, 10, 0, 10),
            Rect(10, 10, 0, 5),
            Rect(10, 5, 0, 10),
            Rect(10, 10, 0, 5)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(7, 0, 0, 4),
            Rect(7, 3, 0, 0),
            Rect(7, 0, 0, 0),
            Rect(7, 0, 0, 4),
            Rect(7, 3, 0, 0),
            Rect(7, 0, 0, 4)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL reversed staggered - multiple rows`() {
        decoration().getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 10, 10, 5),
            Rect(0, 5, 10, 10),
            Rect(0, 10, 10, 10),
            Rect(0, 10, 10, 5),
            Rect(0, 5, 10, 10),
            Rect(0, 10, 10, 5)
        )

        decoration(size = 7, areSideDividersVisible = false).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 0, 7, 4),
            Rect(0, 3, 7, 0),
            Rect(0, 0, 7, 0),
            Rect(0, 0, 7, 4),
            Rect(0, 3, 7, 0),
            Rect(0, 0, 7, 4)
        )

        decoration(size = 1).getItemOffsets(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(0, false),
            view(1, false),
            view(0, true),
            view(0, false),
            view(1, false),
            view(0, false)
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1)
        )
    }

    @Test(expected = IllegalLayoutManagerException::class)
    fun `onDraw - LinearLayoutManager - IllegalLayoutManagerException thrown`() {
        decoration().onDraw(linearLayoutManager(), false, View(context))
    }

    @Test(expected = IllegalLayoutManagerException::class)
    fun `onDraw - custom layout manager - IllegalLayoutManagerException thrown`() {
        decoration().onDraw(customLayoutManager(), false, View(context))
    }

    @Test
    fun `onDraw - vertical staggered, 1 column`() {
        decoration().onDraw(
            staggeredLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138),
            Rect(120, 162, 130, 300),
            Rect(10, 162, 20, 300),
            Rect(20, 162, 120, 172),
            Rect(20, 290, 120, 300),
            Rect(123, 318, 133, 436),
            Rect(3, 318, 13, 436),
            Rect(13, 318, 123, 328),
            Rect(13, 426, 123, 436)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 9, 126, 16),
            Rect(15, 128, 126, 135),
            Rect(20, 165, 120, 172),
            Rect(20, 290, 120, 297),
            Rect(13, 321, 123, 328),
            Rect(13, 426, 123, 433)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(120, 171, 121, 291),
            Rect(19, 171, 20, 291),
            Rect(20, 171, 120, 172),
            Rect(20, 290, 120, 291),
            Rect(123, 327, 124, 427),
            Rect(12, 327, 13, 427),
            Rect(13, 327, 123, 328),
            Rect(13, 426, 123, 427)
        )
    }

    @Test
    fun `onDraw - vertical reversed staggered, 1 column`() {
        decoration().onDraw(
            staggeredLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(0, false).bounds(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(0, false).bounds(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(123, 318, 133, 436),
            Rect(3, 318, 13, 436),
            Rect(13, 426, 123, 436),
            Rect(13, 318, 123, 328),
            Rect(120, 162, 130, 300),
            Rect(10, 162, 20, 300),
            Rect(20, 290, 120, 300),
            Rect(20, 162, 120, 172),
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 128, 126, 138),
            Rect(15, 6, 126, 16)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(0, false).bounds(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(0, false).bounds(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 426, 123, 433),
            Rect(13, 321, 123, 328),
            Rect(20, 290, 120, 297),
            Rect(20, 165, 120, 172),
            Rect(15, 128, 126, 135),
            Rect(15, 9, 126, 16)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(0, false).bounds(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(0, false).bounds(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(123, 327, 124, 427),
            Rect(12, 327, 13, 427),
            Rect(13, 426, 123, 427),
            Rect(13, 327, 123, 328),
            Rect(120, 171, 121, 291),
            Rect(19, 171, 20, 291),
            Rect(20, 290, 120, 291),
            Rect(20, 171, 120, 172),
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 128, 126, 129),
            Rect(15, 15, 126, 16)
        )
    }

    @Test
    fun `onDraw - horizontal staggered, 1 row`() {
        decoration().onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138),
            Rect(280, 2, 290, 140),
            Rect(170, 2, 180, 140),
            Rect(180, 2, 280, 12),
            Rect(180, 130, 280, 140),
            Rect(423, 3, 433, 136),
            Rect(323, 3, 333, 136),
            Rect(333, 3, 423, 13),
            Rect(333, 126, 423, 136)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 9, 133, 135),
            Rect(8, 9, 15, 135),
            Rect(280, 5, 287, 137),
            Rect(173, 5, 180, 137),
            Rect(423, 6, 430, 133),
            Rect(326, 6, 333, 133)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(280, 11, 281, 131),
            Rect(179, 11, 180, 131),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(423, 12, 424, 127),
            Rect(332, 12, 333, 127),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal RTL staggered, 1 row`() {
        decoration().onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(323, 3, 333, 136),
            Rect(423, 3, 433, 136),
            Rect(333, 3, 423, 13),
            Rect(333, 126, 423, 136),
            Rect(170, 2, 180, 140),
            Rect(280, 2, 290, 140),
            Rect(180, 2, 280, 12),
            Rect(180, 130, 280, 140),
            Rect(5, 6, 15, 138),
            Rect(126, 6, 136, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(326, 6, 333, 133),
            Rect(423, 6, 430, 133),
            Rect(173, 5, 180, 137),
            Rect(280, 5, 287, 137),
            Rect(8, 9, 15, 135),
            Rect(126, 9, 133, 135)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(332, 12, 333, 127),
            Rect(423, 12, 424, 127),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127),
            Rect(179, 11, 180, 131),
            Rect(280, 11, 281, 131),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(14, 15, 15, 129),
            Rect(126, 15, 127, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )
    }

    @Test
    fun `onDraw - horizontal reversed staggered, 1 row`() {
        decoration().onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(323, 3, 333, 136),
            Rect(423, 3, 433, 136),
            Rect(333, 3, 423, 13),
            Rect(333, 126, 423, 136),
            Rect(170, 2, 180, 140),
            Rect(280, 2, 290, 140),
            Rect(180, 2, 280, 12),
            Rect(180, 130, 280, 140),
            Rect(5, 6, 15, 138),
            Rect(126, 6, 136, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(326, 6, 333, 133),
            Rect(423, 6, 430, 133),
            Rect(173, 5, 180, 137),
            Rect(280, 5, 287, 137),
            Rect(8, 9, 15, 135),
            Rect(126, 9, 133, 135)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(332, 12, 333, 127),
            Rect(423, 12, 424, 127),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127),
            Rect(179, 11, 180, 131),
            Rect(280, 11, 281, 131),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(14, 15, 15, 129),
            Rect(126, 15, 127, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal RTL reversed staggered, 1 row`() {
        decoration().onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138),
            Rect(280, 2, 290, 140),
            Rect(170, 2, 180, 140),
            Rect(180, 2, 280, 12),
            Rect(180, 130, 280, 140),
            Rect(423, 3, 433, 136),
            Rect(323, 3, 333, 136),
            Rect(333, 3, 423, 13),
            Rect(333, 126, 423, 136)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 9, 133, 135),
            Rect(8, 9, 15, 135),
            Rect(280, 5, 287, 137),
            Rect(173, 5, 180, 137),
            Rect(423, 6, 430, 133),
            Rect(326, 6, 333, 133)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(0, false).bounds(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(0, false).bounds(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(280, 11, 281, 131),
            Rect(179, 11, 180, 131),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(423, 12, 424, 127),
            Rect(332, 12, 333, 127),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127)
        )
    }

    @Test
    fun `onDraw - vertical staggered, multiple columns`() {
        decoration().onDraw(
            staggeredLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(180, 20, 280, 120).margins(4, 3, 2, 1),
            view(0, true).bounds(20, 180, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 340, 120, 420).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138),
            Rect(282, 7, 292, 131),
            Rect(166, 7, 176, 131),
            Rect(176, 7, 282, 17),
            Rect(176, 121, 282, 131),
            Rect(286, 166, 296, 298),
            Rect(8, 166, 18, 298),
            Rect(18, 166, 286, 176),
            Rect(18, 288, 286, 298),
            Rect(123, 324, 133, 432),
            Rect(3, 324, 13, 432),
            Rect(13, 324, 123, 334),
            Rect(13, 422, 123, 432)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(180, 20, 280, 120).margins(4, 3, 2, 1),
            view(0, true).bounds(20, 180, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 340, 120, 420).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 9, 133, 135),
            Rect(15, 9, 126, 16),
            Rect(15, 128, 126, 135),
            Rect(169, 10, 176, 128),
            Rect(176, 10, 282, 17),
            Rect(176, 121, 282, 128),
            Rect(18, 169, 286, 176),
            Rect(18, 288, 286, 295),
            Rect(123, 327, 130, 429),
            Rect(13, 327, 123, 334),
            Rect(13, 422, 123, 429)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(180, 20, 280, 120).margins(4, 3, 2, 1),
            view(0, true).bounds(20, 180, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 340, 120, 420).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(282, 16, 283, 122),
            Rect(175, 16, 176, 122),
            Rect(176, 16, 282, 17),
            Rect(176, 121, 282, 122),
            Rect(286, 175, 287, 289),
            Rect(17, 175, 18, 289),
            Rect(18, 175, 286, 176),
            Rect(18, 288, 286, 289),
            Rect(123, 333, 124, 423),
            Rect(12, 333, 13, 423),
            Rect(13, 333, 123, 334),
            Rect(13, 422, 123, 423)
        )
    }

    @Test
    fun `onDraw - vertical reversed staggered, multiple columns`() {
        decoration().onDraw(
            staggeredLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(0, false).bounds(20, 340, 120, 420).margins(7, 6, 3, 2),
            view(0, true).bounds(20, 180, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(180, 20, 280, 120).margins(4, 3, 2, 1)
        ).assertEquals(
            Rect(123, 324, 133, 432),
            Rect(3, 324, 13, 432),
            Rect(13, 422, 123, 432),
            Rect(13, 324, 123, 334),
            Rect(286, 166, 296, 298),
            Rect(8, 166, 18, 298),
            Rect(18, 288, 286, 298),
            Rect(18, 166, 286, 176),
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 128, 126, 138),
            Rect(15, 6, 126, 16),
            Rect(282, 7, 292, 131),
            Rect(166, 7, 176, 131),
            Rect(176, 121, 282, 131),
            Rect(176, 7, 282, 17)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(0, false).bounds(20, 340, 120, 420).margins(7, 6, 3, 2),
            view(0, true).bounds(20, 180, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(180, 20, 280, 120).margins(4, 3, 2, 1)
        ).assertEquals(
            Rect(123, 327, 130, 429),
            Rect(13, 422, 123, 429),
            Rect(13, 327, 123, 334),
            Rect(18, 288, 286, 295),
            Rect(18, 169, 286, 176),
            Rect(126, 9, 133, 135),
            Rect(15, 128, 126, 135),
            Rect(15, 9, 126, 16),
            Rect(169, 10, 176, 128),
            Rect(176, 121, 282, 128),
            Rect(176, 10, 282, 17)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(0, false).bounds(20, 340, 120, 420).margins(7, 6, 3, 2),
            view(0, true).bounds(20, 180, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(180, 20, 280, 120).margins(4, 3, 2, 1)
        ).assertEquals(
            Rect(123, 333, 124, 423),
            Rect(12, 333, 13, 423),
            Rect(13, 422, 123, 423),
            Rect(13, 333, 123, 334),
            Rect(286, 175, 287, 289),
            Rect(17, 175, 18, 289),
            Rect(18, 288, 286, 289),
            Rect(18, 175, 286, 176),
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 128, 126, 129),
            Rect(15, 15, 126, 16),
            Rect(282, 16, 283, 122),
            Rect(175, 16, 176, 122),
            Rect(176, 121, 282, 122),
            Rect(176, 16, 282, 17)
        )
    }

    @Test
    fun `onDraw - horizontal staggered, multiple rows`() {
        decoration().onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(20, 180, 130, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(340, 20, 420, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138),
            Rect(132, 167, 142, 291),
            Rect(6, 167, 16, 291),
            Rect(16, 167, 132, 177),
            Rect(16, 281, 132, 291),
            Rect(286, 6, 296, 298),
            Rect(168, 6, 178, 298),
            Rect(178, 6, 286, 16),
            Rect(178, 288, 286, 298),
            Rect(423, 4, 433, 142),
            Rect(323, 4, 333, 142),
            Rect(333, 4, 423, 14),
            Rect(333, 132, 423, 142)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(20, 180, 130, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(340, 20, 420, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 9, 133, 135),
            Rect(8, 9, 15, 135),
            Rect(15, 128, 126, 135),
            Rect(132, 170, 139, 288),
            Rect(9, 170, 16, 288),
            Rect(16, 170, 132, 177),
            Rect(286, 9, 293, 295),
            Rect(171, 9, 178, 295),
            Rect(423, 7, 430, 139),
            Rect(326, 7, 333, 139),
            Rect(333, 132, 423, 139)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(20, 180, 130, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(340, 20, 420, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(132, 176, 133, 282),
            Rect(15, 176, 16, 282),
            Rect(16, 176, 132, 177),
            Rect(16, 281, 132, 282),
            Rect(286, 15, 287, 289),
            Rect(177, 15, 178, 289),
            Rect(178, 15, 286, 16),
            Rect(178, 288, 286, 289),
            Rect(423, 13, 424, 133),
            Rect(332, 13, 333, 133),
            Rect(333, 13, 423, 14),
            Rect(333, 132, 423, 133)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal RTL staggered, multiple rows`() {
        decoration().onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(0, false).bounds(340, 20, 420, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(340, 180, 420, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(325, 6, 335, 138),
            Rect(426, 6, 436, 138),
            Rect(335, 6, 426, 16),
            Rect(335, 128, 426, 138),
            Rect(326, 167, 336, 291),
            Rect(422, 167, 432, 291),
            Rect(336, 167, 422, 177),
            Rect(336, 281, 422, 291),
            Rect(168, 6, 178, 298),
            Rect(286, 6, 296, 298),
            Rect(178, 6, 286, 16),
            Rect(178, 288, 286, 298),
            Rect(3, 4, 13, 142),
            Rect(123, 4, 133, 142),
            Rect(13, 4, 123, 14),
            Rect(13, 132, 123, 142)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(0, false).bounds(340, 20, 420, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(340, 180, 420, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(328, 9, 335, 135),
            Rect(426, 9, 433, 135),
            Rect(335, 128, 426, 135),
            Rect(329, 170, 336, 288),
            Rect(422, 170, 429, 288),
            Rect(336, 170, 422, 177),
            Rect(171, 9, 178, 295),
            Rect(286, 9, 293, 295),
            Rect(6, 7, 13, 139),
            Rect(123, 7, 130, 139),
            Rect(13, 132, 123, 139)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(0, false).bounds(340, 20, 420, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(340, 180, 420, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(334, 15, 335, 129),
            Rect(426, 15, 427, 129),
            Rect(335, 15, 426, 16),
            Rect(335, 128, 426, 129),
            Rect(335, 176, 336, 282),
            Rect(422, 176, 423, 282),
            Rect(336, 176, 422, 177),
            Rect(336, 281, 422, 282),
            Rect(177, 15, 178, 289),
            Rect(286, 15, 287, 289),
            Rect(178, 15, 286, 16),
            Rect(178, 288, 286, 289),
            Rect(12, 13, 13, 133),
            Rect(123, 13, 124, 133),
            Rect(13, 13, 123, 14),
            Rect(13, 132, 123, 133)
        )
    }

    @Test
    fun `onDraw - horizontal reversed staggered, multiple rows`() {
        decoration().onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(0, false).bounds(340, 20, 420, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(340, 180, 420, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(325, 6, 335, 138),
            Rect(426, 6, 436, 138),
            Rect(335, 6, 426, 16),
            Rect(335, 128, 426, 138),
            Rect(326, 167, 336, 291),
            Rect(422, 167, 432, 291),
            Rect(336, 167, 422, 177),
            Rect(336, 281, 422, 291),
            Rect(168, 6, 178, 298),
            Rect(286, 6, 296, 298),
            Rect(178, 6, 286, 16),
            Rect(178, 288, 286, 298),
            Rect(3, 4, 13, 142),
            Rect(123, 4, 133, 142),
            Rect(13, 4, 123, 14),
            Rect(13, 132, 123, 142)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(0, false).bounds(340, 20, 420, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(340, 180, 420, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(328, 9, 335, 135),
            Rect(426, 9, 433, 135),
            Rect(335, 128, 426, 135),
            Rect(329, 170, 336, 288),
            Rect(422, 170, 429, 288),
            Rect(336, 170, 422, 177),
            Rect(171, 9, 178, 295),
            Rect(286, 9, 293, 295),
            Rect(6, 7, 13, 139),
            Rect(123, 7, 130, 139),
            Rect(13, 132, 123, 139)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(0, false).bounds(340, 20, 420, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(340, 180, 420, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(20, 20, 120, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(334, 15, 335, 129),
            Rect(426, 15, 427, 129),
            Rect(335, 15, 426, 16),
            Rect(335, 128, 426, 129),
            Rect(335, 176, 336, 282),
            Rect(422, 176, 423, 282),
            Rect(336, 176, 422, 177),
            Rect(336, 281, 422, 282),
            Rect(177, 15, 178, 289),
            Rect(286, 15, 287, 289),
            Rect(178, 15, 286, 16),
            Rect(178, 288, 286, 289),
            Rect(12, 13, 13, 133),
            Rect(123, 13, 124, 133),
            Rect(13, 13, 123, 14),
            Rect(13, 132, 123, 133)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal reversed RTL staggered, multiple rows`() {
        decoration().onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(20, 180, 130, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(340, 20, 420, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 6, 136, 138),
            Rect(5, 6, 15, 138),
            Rect(15, 6, 126, 16),
            Rect(15, 128, 126, 138),
            Rect(132, 167, 142, 291),
            Rect(6, 167, 16, 291),
            Rect(16, 167, 132, 177),
            Rect(16, 281, 132, 291),
            Rect(286, 6, 296, 298),
            Rect(168, 6, 178, 298),
            Rect(178, 6, 286, 16),
            Rect(178, 288, 286, 298),
            Rect(423, 4, 433, 142),
            Rect(323, 4, 333, 142),
            Rect(333, 4, 423, 14),
            Rect(333, 132, 423, 142)
        )

        decoration(size = 7, areSideDividersVisible = false).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(20, 180, 130, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(340, 20, 420, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 9, 133, 135),
            Rect(8, 9, 15, 135),
            Rect(15, 128, 126, 135),
            Rect(132, 170, 139, 288),
            Rect(9, 170, 16, 288),
            Rect(16, 170, 132, 177),
            Rect(286, 9, 293, 295),
            Rect(171, 9, 178, 295),
            Rect(423, 7, 430, 139),
            Rect(326, 7, 333, 139),
            Rect(333, 132, 423, 139)
        )

        decoration(size = 1).onDraw(
            staggeredLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(0, false).bounds(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(1, false).bounds(20, 180, 130, 280).margins(4, 3, 2, 1),
            view(0, true).bounds(180, 20, 280, 280).margins(2, 4, 6, 8),
            view(0, false).bounds(340, 20, 420, 130).margins(7, 6, 3, 2)
        ).assertEquals(
            Rect(126, 15, 127, 129),
            Rect(14, 15, 15, 129),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(132, 176, 133, 282),
            Rect(15, 176, 16, 282),
            Rect(16, 176, 132, 177),
            Rect(16, 281, 132, 282),
            Rect(286, 15, 287, 289),
            Rect(177, 15, 178, 289),
            Rect(178, 15, 286, 16),
            Rect(178, 288, 286, 289),
            Rect(423, 13, 424, 133),
            Rect(332, 13, 333, 133),
            Rect(333, 13, 423, 14),
            Rect(333, 132, 423, 133)
        )
    }

    private fun decoration(
        size: Int = 10,
        areSideDividersVisible: Boolean = true
    ): StaggeredDividerItemDecoration = StaggeredDividerItemDecoration(
        asSpace = false,
        drawable = ColorDrawable(Color.RED),
        size = size,
        areSideDividersVisible = areSideDividersVisible
    )

    private fun view(spanIndex: Int, isFullSpan: Boolean): View {
        val params = StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.isFullSpan = isFullSpan
        val spiedParams = spy(params) {
            doReturn(spanIndex).whenever(it).spanIndex
        }
        return View(context).apply { layoutParams = spiedParams }
    }
}
