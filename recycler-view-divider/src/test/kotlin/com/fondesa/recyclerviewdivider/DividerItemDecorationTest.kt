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
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.drawable.DrawableProviderImpl
import com.fondesa.recyclerviewdivider.inset.InsetProviderImpl
import com.fondesa.recyclerviewdivider.offset.DividerOffsetProviderImpl
import com.fondesa.recyclerviewdivider.size.SizeProviderImpl
import com.fondesa.recyclerviewdivider.test.assertEquals
import com.fondesa.recyclerviewdivider.test.bounds
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.customLayoutManager
import com.fondesa.recyclerviewdivider.test.getItemOffsets
import com.fondesa.recyclerviewdivider.test.gridLayoutManager
import com.fondesa.recyclerviewdivider.test.linearLayoutManager
import com.fondesa.recyclerviewdivider.test.margins
import com.fondesa.recyclerviewdivider.test.onDraw
import com.fondesa.recyclerviewdivider.test.staggeredLayoutManager
import com.fondesa.recyclerviewdivider.tint.TintProviderImpl
import com.fondesa.recyclerviewdivider.visibility.VisibilityProviderImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Tests of [DividerItemDecoration].
 */
@RunWith(AndroidJUnit4::class)
class DividerItemDecorationTest {
    @Test(expected = IllegalLayoutManagerException::class)
    fun `getItemOffsets - StaggeredGridLayoutManager - IllegalLayoutManagerException thrown`() {
        decoration().getItemOffsets(staggeredLayoutManager(), false, 1)
    }

    @Test(expected = IllegalLayoutManagerException::class)
    fun `getItemOffsets - custom layout manager - IllegalLayoutManagerException thrown`() {
        decoration().getItemOffsets(customLayoutManager(), false, 1)
    }

    @Test
    fun `getItemOffsets - vertical LinearLayoutManager`() {
        decoration().getItemOffsets(
            linearLayoutManager(Orientation.VERTICAL, false),
            false,
            3
        ).assertEquals(
            Rect(0, 0, 0, 10),
            Rect(0, 0, 0, 10),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.VERTICAL, false),
            false,
            3
        ).assertEquals(
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 0)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.VERTICAL, false),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 0, 7, 7),
            Rect(7, 0, 7, 7)
        )
    }

    @Test
    fun `getItemOffsets - vertical reversed LinearLayoutManager`() {
        decoration().getItemOffsets(
            linearLayoutManager(Orientation.VERTICAL, true),
            false,
            3
        ).assertEquals(
            Rect(0, 10, 0, 0),
            Rect(0, 10, 0, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.VERTICAL, true),
            false,
            3
        ).assertEquals(
            Rect(1, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(1, 0, 1, 0)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.VERTICAL, true),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 7, 7, 0),
            Rect(7, 7, 7, 0)
        )
    }

    @Test
    fun `getItemOffsets - horizontal LinearLayoutManager`() {
        decoration().getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            false,
            3
        ).assertEquals(
            Rect(0, 0, 10, 0),
            Rect(0, 0, 10, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            false,
            3
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(0, 7, 7, 7),
            Rect(0, 7, 7, 7)
        )
    }

    @Test
    fun `getItemOffsets - horizontal reversed LinearLayoutManager`() {
        decoration().getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            false,
            3
        ).assertEquals(
            Rect(10, 0, 0, 0),
            Rect(10, 0, 0, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            false,
            3
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 7, 0, 7),
            Rect(7, 7, 0, 7)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL LinearLayoutManager`() {
        decoration().getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            true,
            3
        ).assertEquals(
            Rect(10, 0, 0, 0),
            Rect(10, 0, 0, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            true,
            3
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            true,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 7, 0, 7),
            Rect(7, 7, 0, 7)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal reversed RTL LinearLayoutManager`() {
        decoration().getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            true,
            3
        ).assertEquals(
            Rect(0, 0, 10, 0),
            Rect(0, 0, 10, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            true,
            3
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            true,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(0, 7, 7, 7),
            Rect(0, 7, 7, 7)
        )
    }

    @Test
    fun `getItemOffsets - vertical GridLayoutManager, 1 column`() {
        decoration().getItemOffsets(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            false,
            3
        ).assertEquals(
            Rect(0, 0, 0, 10),
            Rect(0, 0, 0, 10),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            false,
            3
        ).assertEquals(
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(1, 0, 1, 0)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 0, 7, 7),
            Rect(7, 0, 7, 7)
        )
    }

    @Test
    fun `getItemOffsets - vertical reversed GridLayoutManager, 1 column`() {
        decoration().getItemOffsets(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            false,
            3
        ).assertEquals(
            Rect(0, 10, 0, 0),
            Rect(0, 10, 0, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            false,
            3
        ).assertEquals(
            Rect(1, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(1, 0, 1, 0)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 7, 7, 0),
            Rect(7, 7, 7, 0)
        )
    }

    @Test
    fun `getItemOffsets - horizontal GridLayoutManager, 1 row`() {
        decoration().getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            3
        ).assertEquals(
            Rect(0, 0, 10, 0),
            Rect(0, 0, 10, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            3
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(0, 7, 7, 7),
            Rect(0, 7, 7, 7)
        )
    }

    @Test
    fun `getItemOffsets - horizontal reversed GridLayoutManager, 1 row`() {
        decoration().getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            3
        ).assertEquals(
            Rect(10, 0, 0, 0),
            Rect(10, 0, 0, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            3
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 7, 0, 7),
            Rect(7, 7, 0, 7)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL GridLayoutManager, 1 row`() {
        decoration().getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            3
        ).assertEquals(
            Rect(10, 0, 0, 0),
            Rect(10, 0, 0, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            3
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(7, 7, 0, 7),
            Rect(7, 7, 0, 7)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal reversed RTL GridLayoutManager, 1 row`() {
        decoration().getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            3
        ).assertEquals(
            Rect(0, 0, 10, 0),
            Rect(0, 0, 10, 0),
            Rect(0, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            3
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            3
        ).assertEquals(
            Rect(7, 7, 7, 7),
            Rect(0, 7, 7, 7),
            Rect(0, 7, 7, 7)
        )
    }

    @Test
    fun `getItemOffsets - vertical GridLayoutManager, multiple columns`() {
        decoration().getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            false,
            6
        ).assertEquals(
            Rect(0, 0, 5, 10),
            Rect(5, 0, 0, 10),
            Rect(0, 0, 5, 10),
            Rect(5, 0, 0, 10),
            Rect(0, 0, 5, 0),
            Rect(5, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            false,
            7
        ).assertEquals(
            Rect(1, 0, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(1, 0, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(1, 0, 1, 0)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            false,
            6
        ).assertEquals(
            Rect(7, 7, 4, 7),
            Rect(3, 7, 7, 7),
            Rect(7, 0, 4, 7),
            Rect(3, 0, 7, 7),
            Rect(7, 0, 4, 7),
            Rect(3, 0, 7, 7)
        )

        decoration(
            size = 9,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, false).withLookup(),
            false,
            7
        ).assertEquals(
            Rect(9, 0, 9, 9),
            Rect(9, 0, 5, 9),
            Rect(4, 0, 9, 9),
            Rect(9, 0, 9, 9),
            Rect(9, 0, 5, 9),
            Rect(4, 0, 9, 9),
            Rect(9, 0, 9, 9)
        )
    }

    @Test
    fun `getItemOffsets - vertical reversed GridLayoutManager, multiple columns`() {
        decoration().getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            false,
            6
        ).assertEquals(
            Rect(0, 10, 5, 0),
            Rect(5, 10, 0, 0),
            Rect(0, 10, 5, 0),
            Rect(5, 10, 0, 0),
            Rect(0, 0, 5, 0),
            Rect(5, 0, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            false,
            7
        ).assertEquals(
            Rect(1, 1, 1, 0),
            Rect(0, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(0, 1, 1, 0),
            Rect(1, 1, 1, 0),
            Rect(0, 1, 1, 0),
            Rect(1, 0, 1, 0)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            false,
            6
        ).assertEquals(
            Rect(7, 7, 4, 7),
            Rect(3, 7, 7, 7),
            Rect(7, 7, 4, 0),
            Rect(3, 7, 7, 0),
            Rect(7, 7, 4, 0),
            Rect(3, 7, 7, 0)
        )

        decoration(
            size = 9,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.VERTICAL, true).withLookup(),
            false,
            7
        ).assertEquals(
            Rect(9, 9, 9, 0),
            Rect(9, 9, 5, 0),
            Rect(4, 9, 9, 0),
            Rect(9, 9, 9, 0),
            Rect(9, 9, 5, 0),
            Rect(4, 9, 9, 0),
            Rect(9, 9, 9, 0)
        )
    }

    @Test
    fun `getItemOffsets - horizontal GridLayoutManager, multiple rows`() {
        decoration().getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            6
        ).assertEquals(
            Rect(0, 0, 10, 5),
            Rect(0, 5, 10, 0),
            Rect(0, 0, 10, 5),
            Rect(0, 5, 10, 0),
            Rect(0, 0, 0, 5),
            Rect(0, 5, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            7
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            6
        ).assertEquals(
            Rect(7, 7, 7, 4),
            Rect(7, 3, 7, 7),
            Rect(0, 7, 7, 4),
            Rect(0, 3, 7, 7),
            Rect(0, 7, 7, 4),
            Rect(0, 3, 7, 7)
        )

        decoration(
            size = 9,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false).withLookup(),
            false,
            7
        ).assertEquals(
            Rect(0, 9, 9, 9),
            Rect(0, 9, 9, 5),
            Rect(0, 4, 9, 9),
            Rect(0, 9, 9, 9),
            Rect(0, 9, 9, 5),
            Rect(0, 4, 9, 9),
            Rect(0, 9, 9, 9)
        )
    }

    @Test
    fun `getItemOffsets - horizontal reversed GridLayoutManager, multiple rows`() {
        decoration().getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            6
        ).assertEquals(
            Rect(10, 0, 0, 5),
            Rect(10, 5, 0, 0),
            Rect(10, 0, 0, 5),
            Rect(10, 5, 0, 0),
            Rect(0, 0, 0, 5),
            Rect(0, 5, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            7
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            6
        ).assertEquals(
            Rect(7, 7, 7, 4),
            Rect(7, 3, 7, 7),
            Rect(7, 7, 0, 4),
            Rect(7, 3, 0, 7),
            Rect(7, 7, 0, 4),
            Rect(7, 3, 0, 7)
        )

        decoration(
            size = 9,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true).withLookup(),
            false,
            7
        ).assertEquals(
            Rect(9, 9, 0, 9),
            Rect(9, 9, 0, 5),
            Rect(9, 4, 0, 9),
            Rect(9, 9, 0, 9),
            Rect(9, 9, 0, 5),
            Rect(9, 4, 0, 9),
            Rect(9, 9, 0, 9)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal RTL GridLayoutManager, multiple rows`() {
        decoration().getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            6
        ).assertEquals(
            Rect(10, 0, 0, 5),
            Rect(10, 5, 0, 0),
            Rect(10, 0, 0, 5),
            Rect(10, 5, 0, 0),
            Rect(0, 0, 0, 5),
            Rect(0, 5, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            7
        ).assertEquals(
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(1, 1, 0, 1),
            Rect(1, 0, 0, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            6
        ).assertEquals(
            Rect(7, 7, 7, 4),
            Rect(7, 3, 7, 7),
            Rect(7, 7, 0, 4),
            Rect(7, 3, 0, 7),
            Rect(7, 7, 0, 4),
            Rect(7, 3, 0, 7)
        )

        decoration(
            size = 9,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, false).withLookup(),
            true,
            7
        ).assertEquals(
            Rect(9, 9, 0, 9),
            Rect(9, 9, 0, 5),
            Rect(9, 4, 0, 9),
            Rect(9, 9, 0, 9),
            Rect(9, 9, 0, 5),
            Rect(9, 4, 0, 9),
            Rect(9, 9, 0, 9)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `getItemOffsets - horizontal reversed RTL GridLayoutManager, multiple rows`() {
        decoration().getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            6
        ).assertEquals(
            Rect(0, 0, 10, 5),
            Rect(0, 5, 10, 0),
            Rect(0, 0, 10, 5),
            Rect(0, 5, 10, 0),
            Rect(0, 0, 0, 5),
            Rect(0, 5, 0, 0)
        )

        decoration(
            size = 1,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            7
        ).assertEquals(
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 1, 1),
            Rect(0, 0, 1, 1),
            Rect(0, 1, 0, 1)
        )

        decoration(
            size = 7,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            6
        ).assertEquals(
            Rect(7, 7, 7, 4),
            Rect(7, 3, 7, 7),
            Rect(0, 7, 7, 4),
            Rect(0, 3, 7, 7),
            Rect(0, 7, 7, 4),
            Rect(0, 3, 7, 7)
        )

        decoration(
            size = 9,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).getItemOffsets(
            gridLayoutManager(2, Orientation.HORIZONTAL, true).withLookup(),
            true,
            7
        ).assertEquals(
            Rect(0, 9, 9, 9),
            Rect(0, 9, 9, 5),
            Rect(0, 4, 9, 9),
            Rect(0, 9, 9, 9),
            Rect(0, 9, 9, 5),
            Rect(0, 4, 9, 9),
            Rect(0, 9, 9, 9)
        )
    }

    @Test(expected = IllegalLayoutManagerException::class)
    fun `onDraw - StaggeredGridLayoutManager - IllegalLayoutManagerException thrown`() {
        decoration().onDraw(staggeredLayoutManager(), false, View(context))
    }

    @Test(expected = IllegalLayoutManagerException::class)
    fun `onDraw - custom layout manager - IllegalLayoutManagerException thrown`() {
        decoration().onDraw(customLayoutManager(), false, View(context))
    }

    @Test
    fun `onDraw - vertical LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(20, 290, 120, 300)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 129),
            Rect(14, 16, 15, 129),
            Rect(126, 16, 127, 129),
            Rect(20, 290, 120, 291),
            Rect(19, 172, 20, 291),
            Rect(120, 172, 121, 291),
            Rect(12, 328, 13, 426),
            Rect(123, 328, 124, 426)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(21, 290, 118, 297),
            Rect(13, 173, 20, 288),
            Rect(120, 173, 127, 288),
            Rect(14, 426, 121, 433),
            Rect(6, 329, 13, 424),
            Rect(123, 329, 130, 424)
        )
    }

    @Test
    fun `onDraw - vertical reversed LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 318, 123, 328),
            Rect(20, 162, 120, 172)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 327, 123, 328),
            Rect(12, 327, 13, 426),
            Rect(123, 327, 124, 426),
            Rect(20, 171, 120, 172),
            Rect(19, 171, 20, 290),
            Rect(120, 171, 121, 290),
            Rect(14, 16, 15, 128),
            Rect(126, 16, 127, 128)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(14, 426, 121, 433),
            Rect(14, 321, 121, 328),
            Rect(6, 330, 13, 425),
            Rect(123, 330, 130, 425),
            Rect(21, 165, 118, 172),
            Rect(13, 174, 20, 289),
            Rect(120, 174, 127, 289),
            Rect(16, 9, 124, 16),
            Rect(8, 18, 15, 127),
            Rect(126, 18, 133, 127)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - vertical RTL LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.VERTICAL, false),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(20, 290, 120, 300)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, false),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 129),
            Rect(126, 16, 127, 129),
            Rect(14, 16, 15, 129),
            Rect(20, 290, 120, 291),
            Rect(120, 172, 121, 291),
            Rect(19, 172, 20, 291),
            Rect(123, 328, 124, 426),
            Rect(12, 328, 13, 426)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, false),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(126, 17, 133, 126),
            Rect(8, 17, 15, 126),
            Rect(22, 290, 119, 297),
            Rect(120, 173, 127, 288),
            Rect(13, 173, 20, 288),
            Rect(15, 426, 122, 433),
            Rect(123, 329, 130, 424),
            Rect(6, 329, 13, 424)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - vertical reversed RTL LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.VERTICAL, true),
            true,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 318, 123, 328),
            Rect(20, 162, 120, 172)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, true),
            true,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 327, 123, 328),
            Rect(123, 327, 124, 426),
            Rect(12, 327, 13, 426),
            Rect(20, 171, 120, 172),
            Rect(120, 171, 121, 290),
            Rect(19, 171, 20, 290),
            Rect(126, 16, 127, 128),
            Rect(14, 16, 15, 128)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.VERTICAL, true),
            true,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(15, 426, 122, 433),
            Rect(15, 321, 122, 328),
            Rect(123, 330, 130, 425),
            Rect(6, 330, 13, 425),
            Rect(22, 165, 119, 172),
            Rect(120, 174, 127, 289),
            Rect(13, 174, 20, 289),
            Rect(17, 9, 125, 16),
            Rect(126, 18, 133, 127),
            Rect(8, 18, 15, 127)
        )
    }

    @Test
    fun `onDraw - horizontal LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 16, 136, 128),
            Rect(280, 12, 290, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(126, 15, 127, 129),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(280, 11, 281, 131),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(181, 5, 278, 12),
            Rect(181, 130, 278, 137),
            Rect(280, 13, 287, 128),
            Rect(334, 6, 421, 13),
            Rect(334, 126, 421, 133),
            Rect(423, 14, 430, 124)
        )
    }

    @Test
    fun `onDraw - horizontal reversed LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(323, 13, 333, 126),
            Rect(170, 12, 180, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127),
            Rect(332, 12, 333, 127),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(179, 11, 180, 131),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(335, 6, 422, 13),
            Rect(335, 126, 422, 133),
            Rect(423, 14, 430, 124),
            Rect(326, 14, 333, 124),
            Rect(182, 5, 279, 12),
            Rect(182, 130, 279, 137),
            Rect(173, 13, 180, 128),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal RTL LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(323, 13, 333, 126),
            Rect(170, 12, 180, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127),
            Rect(332, 12, 333, 127),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(179, 11, 180, 131),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(335, 6, 422, 13),
            Rect(335, 126, 422, 133),
            Rect(423, 14, 430, 124),
            Rect(326, 14, 333, 124),
            Rect(182, 5, 279, 12),
            Rect(182, 130, 279, 137),
            Rect(173, 13, 180, 128),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal reversed RTL LinearLayoutManager`() {
        decoration().onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 16, 136, 128),
            Rect(280, 12, 290, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(126, 15, 127, 129),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(280, 11, 281, 131),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            linearLayoutManager(Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(181, 5, 278, 12),
            Rect(181, 130, 278, 137),
            Rect(280, 13, 287, 128),
            Rect(334, 6, 421, 13),
            Rect(334, 126, 421, 133),
            Rect(423, 14, 430, 124)
        )
    }

    @Test
    fun `onDraw - vertical GridLayoutManager, 1 column`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(20, 290, 120, 300)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 129),
            Rect(14, 16, 15, 129),
            Rect(126, 16, 127, 129),
            Rect(20, 290, 120, 291),
            Rect(19, 172, 20, 291),
            Rect(120, 172, 121, 291),
            Rect(12, 328, 13, 426),
            Rect(123, 328, 124, 426)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(21, 290, 118, 297),
            Rect(13, 173, 20, 288),
            Rect(120, 173, 127, 288),
            Rect(14, 426, 121, 433),
            Rect(6, 329, 13, 424),
            Rect(123, 329, 130, 424)
        )
    }

    @Test
    fun `onDraw - vertical reversed GridLayoutManager, 1 column`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 318, 123, 328),
            Rect(20, 162, 120, 172)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 327, 123, 328),
            Rect(12, 327, 13, 426),
            Rect(123, 327, 124, 426),
            Rect(20, 171, 120, 172),
            Rect(19, 171, 20, 290),
            Rect(120, 171, 121, 290),
            Rect(14, 16, 15, 128),
            Rect(126, 16, 127, 128)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(14, 426, 121, 433),
            Rect(14, 321, 121, 328),
            Rect(6, 330, 13, 425),
            Rect(123, 330, 130, 425),
            Rect(21, 165, 118, 172),
            Rect(13, 174, 20, 289),
            Rect(120, 174, 127, 289),
            Rect(16, 9, 124, 16),
            Rect(8, 18, 15, 127),
            Rect(126, 18, 133, 127)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - vertical RTL GridLayoutManager, 1 column`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(20, 290, 120, 300)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 129),
            Rect(126, 16, 127, 129),
            Rect(14, 16, 15, 129),
            Rect(20, 290, 120, 291),
            Rect(120, 172, 121, 291),
            Rect(19, 172, 20, 291),
            Rect(123, 328, 124, 426),
            Rect(12, 328, 13, 426)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, false),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 340, 120, 420).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(126, 17, 133, 126),
            Rect(8, 17, 15, 126),
            Rect(22, 290, 119, 297),
            Rect(120, 173, 127, 288),
            Rect(13, 173, 20, 288),
            Rect(15, 426, 122, 433),
            Rect(123, 329, 130, 424),
            Rect(6, 329, 13, 424)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - vertical reversed RTL GridLayoutManager, 1 column`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            true,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 318, 123, 328),
            Rect(20, 162, 120, 172)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            true,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(13, 327, 123, 328),
            Rect(123, 327, 124, 426),
            Rect(12, 327, 13, 426),
            Rect(20, 171, 120, 172),
            Rect(120, 171, 121, 290),
            Rect(19, 171, 20, 290),
            Rect(126, 16, 127, 128),
            Rect(14, 16, 15, 128)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.VERTICAL, true),
            true,
            view(20, 340, 120, 420).margins(7, 12, 3, 6),
            view(20, 180, 120, 280).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(15, 426, 122, 433),
            Rect(15, 321, 122, 328),
            Rect(123, 330, 130, 425),
            Rect(6, 330, 13, 425),
            Rect(22, 165, 119, 172),
            Rect(120, 174, 127, 289),
            Rect(13, 174, 20, 289),
            Rect(17, 9, 125, 16),
            Rect(126, 18, 133, 127),
            Rect(8, 18, 15, 127)
        )
    }

    @Test
    fun `onDraw - horizontal GridLayoutManager, 1 row`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 16, 136, 128),
            Rect(280, 12, 290, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(126, 15, 127, 129),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(280, 11, 281, 131),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(181, 5, 278, 12),
            Rect(181, 130, 278, 137),
            Rect(280, 13, 287, 128),
            Rect(334, 6, 421, 13),
            Rect(334, 126, 421, 133),
            Rect(423, 14, 430, 124)
        )
    }

    @Test
    fun `onDraw - horizontal reversed GridLayoutManager, 1 row`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(323, 13, 333, 126),
            Rect(170, 12, 180, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127),
            Rect(332, 12, 333, 127),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(179, 11, 180, 131),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(335, 6, 422, 13),
            Rect(335, 126, 422, 133),
            Rect(423, 14, 430, 124),
            Rect(326, 14, 333, 124),
            Rect(182, 5, 279, 12),
            Rect(182, 130, 279, 137),
            Rect(173, 13, 180, 128),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal RTL GridLayoutManager, 1 row`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(323, 13, 333, 126),
            Rect(170, 12, 180, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127),
            Rect(332, 12, 333, 127),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(179, 11, 180, 131),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 7, 3, 6),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(335, 6, 422, 13),
            Rect(335, 126, 422, 133),
            Rect(423, 14, 430, 124),
            Rect(326, 14, 333, 124),
            Rect(182, 5, 279, 12),
            Rect(182, 130, 279, 137),
            Rect(173, 13, 180, 128),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal reversed RTL GridLayoutManager, 1 row`() {
        decoration().onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(126, 16, 136, 128),
            Rect(280, 12, 290, 130)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(126, 15, 127, 129),
            Rect(180, 11, 280, 12),
            Rect(180, 130, 280, 131),
            Rect(280, 11, 281, 131),
            Rect(333, 12, 423, 13),
            Rect(333, 126, 423, 127)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(1, Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(0, 8, 0, 10),
            view(340, 20, 420, 120).margins(7, 7, 3, 6)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(181, 5, 278, 12),
            Rect(181, 130, 278, 137),
            Rect(280, 13, 287, 128),
            Rect(334, 6, 421, 13),
            Rect(334, 126, 421, 133),
            Rect(423, 14, 430, 124)
        )
    }

    @Test
    fun `onDraw - vertical GridLayoutManager, multiple columns`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(126, 16, 136, 138),
            Rect(175, 123, 286, 133),
            Rect(18, 290, 120, 300),
            Rect(120, 177, 130, 300),
            Rect(180, 290, 281, 300),
            Rect(120, 336, 130, 426)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 500, 120, 580).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(15, 128, 126, 129),
            Rect(14, 16, 15, 129),
            Rect(126, 16, 127, 129),
            Rect(175, 123, 286, 124),
            Rect(286, 18, 287, 124),
            Rect(18, 290, 120, 291),
            Rect(17, 177, 18, 291),
            Rect(120, 177, 121, 291),
            Rect(180, 290, 281, 291),
            Rect(281, 172, 282, 291),
            Rect(13, 426, 120, 427),
            Rect(12, 336, 13, 427),
            Rect(120, 336, 121, 427),
            Rect(177, 421, 283, 422),
            Rect(283, 328, 284, 422),
            Rect(12, 488, 13, 586),
            Rect(123, 488, 124, 586)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(176, 11, 284, 18),
            Rect(176, 123, 284, 130),
            Rect(286, 19, 293, 121),
            Rect(19, 290, 118, 297),
            Rect(11, 178, 18, 288),
            Rect(120, 178, 127, 288),
            Rect(181, 290, 279, 297),
            Rect(281, 173, 288, 288),
            Rect(14, 426, 118, 433),
            Rect(6, 337, 13, 424),
            Rect(120, 337, 127, 424),
            Rect(178, 421, 281, 428),
            Rect(283, 329, 290, 419)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false).withLookup(),
            false,
            view(20, 20, 280, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 340, 280, 420).margins(7, 4, 0, 6),
            view(20, 500, 120, 580).margins(2, 12, 1, 8),
            view(180, 500, 280, 580).margins(7, 5, 3, 4),
            view(20, 630, 280, 700).margins(1, 12, 3, 7)
        ).assertEquals(
            Rect(17, 128, 285, 137),
            Rect(6, 18, 15, 127),
            Rect(286, 18, 295, 127),
            Rect(20, 290, 119, 299),
            Rect(9, 179, 18, 289),
            Rect(120, 179, 129, 289),
            Rect(182, 290, 280, 299),
            Rect(281, 174, 290, 289),
            Rect(15, 426, 279, 435),
            Rect(4, 338, 13, 425),
            Rect(280, 338, 289, 425),
            Rect(20, 588, 120, 597),
            Rect(9, 490, 18, 587),
            Rect(121, 490, 130, 587),
            Rect(175, 584, 282, 593),
            Rect(283, 497, 292, 583),
            Rect(21, 707, 282, 716),
            Rect(10, 620, 19, 706),
            Rect(283, 620, 292, 706)
        )
    }

    @Test
    fun `onDraw - vertical reversed GridLayoutManager, multiple columns`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3)
        ).assertEquals(
            Rect(13, 326, 120, 336),
            Rect(120, 326, 130, 426),
            Rect(177, 318, 283, 328),
            Rect(18, 167, 120, 177),
            Rect(120, 167, 130, 290),
            Rect(180, 162, 281, 172),
            Rect(126, 16, 136, 128)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(20, 500, 120, 580).margins(7, 12, 3, 6),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3)
        ).assertEquals(
            Rect(13, 487, 123, 488),
            Rect(12, 487, 13, 586),
            Rect(123, 487, 124, 586),
            Rect(13, 335, 120, 336),
            Rect(120, 335, 121, 426),
            Rect(177, 327, 283, 328),
            Rect(176, 327, 177, 421),
            Rect(283, 327, 284, 421),
            Rect(18, 176, 120, 177),
            Rect(120, 176, 121, 290),
            Rect(180, 171, 281, 172),
            Rect(179, 171, 180, 290),
            Rect(281, 171, 282, 290),
            Rect(15, 15, 126, 16),
            Rect(126, 15, 127, 128),
            Rect(174, 18, 175, 123),
            Rect(286, 18, 287, 123)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            false,
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3)
        ).assertEquals(
            Rect(14, 426, 118, 433),
            Rect(14, 329, 118, 336),
            Rect(6, 338, 13, 425),
            Rect(120, 338, 127, 425),
            Rect(178, 421, 281, 428),
            Rect(178, 321, 281, 328),
            Rect(283, 330, 290, 420),
            Rect(19, 170, 118, 177),
            Rect(11, 179, 18, 289),
            Rect(120, 179, 127, 289),
            Rect(181, 165, 279, 172),
            Rect(281, 174, 288, 289),
            Rect(16, 9, 124, 16),
            Rect(8, 18, 15, 127),
            Rect(126, 18, 133, 127),
            Rect(176, 11, 284, 18),
            Rect(286, 20, 293, 122)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true).withLookup(),
            false,
            view(20, 630, 280, 700).margins(1, 12, 3, 7),
            view(20, 500, 120, 580).margins(2, 12, 1, 8),
            view(180, 500, 280, 580).margins(7, 5, 3, 4),
            view(20, 340, 280, 420).margins(7, 4, 0, 6),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 280, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(21, 609, 282, 618),
            Rect(10, 619, 19, 705),
            Rect(283, 619, 292, 705),
            Rect(20, 479, 120, 488),
            Rect(9, 489, 18, 586),
            Rect(121, 489, 130, 586),
            Rect(175, 486, 282, 495),
            Rect(283, 496, 292, 582),
            Rect(15, 327, 279, 336),
            Rect(4, 337, 13, 424),
            Rect(280, 337, 289, 424),
            Rect(20, 168, 119, 177),
            Rect(9, 178, 18, 288),
            Rect(120, 178, 129, 288),
            Rect(182, 163, 280, 172),
            Rect(281, 173, 290, 288),
            Rect(17, 7, 285, 16),
            Rect(6, 17, 15, 126),
            Rect(286, 17, 295, 126)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - vertical RTL GridLayoutManager, multiple column`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            true,
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 340, 120, 420).margins(7, 4, 0, 6)
        ).assertEquals(
            Rect(175, 123, 286, 133),
            Rect(165, 18, 175, 133),
            Rect(15, 128, 126, 138),
            Rect(180, 290, 281, 300),
            Rect(170, 172, 180, 300),
            Rect(18, 290, 120, 300),
            Rect(167, 328, 177, 421)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            true,
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(20, 500, 120, 580).margins(7, 12, 3, 6)
        ).assertEquals(
            Rect(175, 123, 286, 124),
            Rect(286, 18, 287, 124),
            Rect(174, 18, 175, 124),
            Rect(15, 128, 126, 129),
            Rect(14, 16, 15, 129),
            Rect(180, 290, 281, 291),
            Rect(281, 172, 282, 291),
            Rect(179, 172, 180, 291),
            Rect(18, 290, 120, 291),
            Rect(17, 177, 18, 291),
            Rect(177, 421, 283, 422),
            Rect(283, 328, 284, 422),
            Rect(176, 328, 177, 422),
            Rect(13, 426, 120, 427),
            Rect(12, 336, 13, 427),
            Rect(123, 488, 124, 586),
            Rect(12, 488, 13, 586)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false),
            true,
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(180, 340, 280, 420).margins(7, 4, 0, 6)
        ).assertEquals(
            Rect(177, 11, 285, 18),
            Rect(177, 123, 285, 130),
            Rect(286, 19, 293, 121),
            Rect(168, 19, 175, 121),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126),
            Rect(182, 290, 280, 297),
            Rect(281, 173, 288, 288),
            Rect(173, 173, 180, 288),
            Rect(20, 290, 119, 297),
            Rect(11, 178, 18, 288),
            Rect(179, 421, 282, 428),
            Rect(283, 329, 290, 419),
            Rect(170, 329, 177, 419),
            Rect(175, 426, 279, 433),
            Rect(166, 337, 173, 424)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, false).withLookup(),
            true,
            view(20, 20, 280, 120).margins(5, 4, 6, 8),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(20, 340, 280, 420).margins(7, 4, 0, 6),
            view(180, 500, 280, 580).margins(7, 5, 3, 4),
            view(20, 500, 120, 580).margins(2, 12, 1, 8),
            view(20, 630, 280, 700).margins(1, 12, 3, 7)
        ).assertEquals(
            Rect(16, 128, 284, 137),
            Rect(286, 18, 295, 127),
            Rect(6, 18, 15, 127),
            Rect(181, 290, 279, 299),
            Rect(281, 174, 290, 289),
            Rect(171, 174, 180, 289),
            Rect(19, 290, 118, 299),
            Rect(9, 179, 18, 289),
            Rect(14, 426, 278, 435),
            Rect(280, 338, 289, 425),
            Rect(4, 338, 13, 425),
            Rect(174, 584, 281, 593),
            Rect(283, 497, 292, 583),
            Rect(164, 497, 173, 583),
            Rect(19, 588, 119, 597),
            Rect(9, 490, 18, 587),
            Rect(20, 707, 281, 716),
            Rect(283, 620, 292, 706),
            Rect(10, 620, 19, 706)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - vertical reversed RTL GridLayoutManager, multiple columns`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            true,
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(177, 318, 283, 328),
            Rect(167, 318, 177, 421),
            Rect(13, 326, 120, 336),
            Rect(180, 162, 281, 172),
            Rect(170, 162, 180, 290),
            Rect(18, 167, 120, 177),
            Rect(165, 18, 175, 123)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            true,
            view(180, 500, 280, 580).margins(7, 12, 3, 6),
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(173, 487, 283, 488),
            Rect(283, 487, 284, 586),
            Rect(172, 487, 173, 586),
            Rect(177, 327, 283, 328),
            Rect(176, 327, 177, 421),
            Rect(13, 335, 120, 336),
            Rect(120, 335, 121, 426),
            Rect(12, 335, 13, 426),
            Rect(180, 171, 281, 172),
            Rect(179, 171, 180, 290),
            Rect(18, 176, 120, 177),
            Rect(120, 176, 121, 290),
            Rect(17, 176, 18, 290),
            Rect(175, 17, 286, 18),
            Rect(174, 17, 175, 123),
            Rect(126, 16, 127, 128),
            Rect(14, 16, 15, 128)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true),
            true,
            view(180, 340, 280, 420).margins(3, 12, 3, 1),
            view(20, 340, 120, 420).margins(7, 4, 0, 6),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(179, 421, 282, 428),
            Rect(179, 321, 282, 328),
            Rect(283, 330, 290, 420),
            Rect(170, 330, 177, 420),
            Rect(15, 426, 119, 433),
            Rect(15, 329, 119, 336),
            Rect(6, 338, 13, 425),
            Rect(182, 165, 280, 172),
            Rect(281, 174, 288, 289),
            Rect(173, 174, 180, 289),
            Rect(20, 170, 119, 177),
            Rect(11, 179, 18, 289),
            Rect(177, 11, 285, 18),
            Rect(286, 20, 293, 122),
            Rect(168, 20, 175, 122),
            Rect(17, 9, 125, 16),
            Rect(8, 18, 15, 127)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.VERTICAL, true).withLookup(),
            true,
            view(20, 630, 280, 700).margins(1, 12, 3, 7),
            view(180, 500, 280, 580).margins(7, 5, 3, 4),
            view(20, 500, 120, 580).margins(2, 12, 1, 8),
            view(20, 340, 280, 420).margins(7, 4, 0, 6),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(20, 20, 280, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(20, 609, 281, 618),
            Rect(283, 619, 292, 705),
            Rect(10, 619, 19, 705),
            Rect(174, 486, 281, 495),
            Rect(283, 496, 292, 582),
            Rect(164, 496, 173, 582),
            Rect(19, 479, 119, 488),
            Rect(9, 489, 18, 586),
            Rect(14, 327, 278, 336),
            Rect(280, 337, 289, 424),
            Rect(4, 337, 13, 424),
            Rect(181, 163, 279, 172),
            Rect(281, 173, 290, 288),
            Rect(171, 173, 180, 288),
            Rect(19, 168, 118, 177),
            Rect(9, 178, 18, 288),
            Rect(16, 7, 284, 16),
            Rect(286, 17, 295, 126),
            Rect(6, 17, 15, 126)
        )
    }

    @Test
    fun `onDraw - horizontal GridLayoutManager, multiple rows`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(126, 16, 136, 138),
            Rect(120, 177, 130, 290),
            Rect(175, 123, 286, 133),
            Rect(286, 18, 296, 133),
            Rect(281, 172, 291, 290),
            Rect(333, 126, 420, 136)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(500, 20, 580, 120).margins(2, 2, 2, 1)
        ).assertEquals(
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(126, 15, 127, 129),
            Rect(18, 290, 120, 291),
            Rect(120, 177, 121, 291),
            Rect(175, 17, 286, 18),
            Rect(175, 123, 286, 124),
            Rect(286, 17, 287, 124),
            Rect(180, 290, 281, 291),
            Rect(281, 172, 282, 291),
            Rect(333, 15, 420, 16),
            Rect(333, 126, 420, 127),
            Rect(420, 15, 421, 127),
            Rect(337, 281, 423, 282),
            Rect(423, 168, 424, 282),
            Rect(498, 17, 582, 18),
            Rect(498, 121, 582, 122)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            false,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(19, 290, 118, 297),
            Rect(11, 178, 18, 288),
            Rect(120, 178, 127, 288),
            Rect(176, 11, 284, 18),
            Rect(176, 123, 284, 130),
            Rect(286, 19, 293, 121),
            Rect(181, 290, 279, 297),
            Rect(281, 173, 288, 288),
            Rect(334, 9, 418, 16),
            Rect(334, 126, 418, 133),
            Rect(420, 17, 427, 124),
            Rect(338, 281, 421, 288),
            Rect(423, 169, 430, 279)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false).withLookup(),
            false,
            view(20, 20, 120, 280).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 280).margins(7, 4, 0, 6),
            view(500, 20, 580, 120).margins(2, 2, 2, 1),
            view(500, 180, 580, 280).margins(5, 2, 2, 6),
            view(630, 20, 700, 120).margins(2, 6, 2, 1)
        ).assertEquals(
            Rect(17, 7, 125, 16),
            Rect(17, 288, 125, 297),
            Rect(126, 18, 135, 287),
            Rect(177, 9, 285, 18),
            Rect(177, 123, 285, 132),
            Rect(286, 20, 295, 122),
            Rect(182, 290, 280, 299),
            Rect(281, 174, 290, 289),
            Rect(335, 7, 419, 16),
            Rect(335, 286, 419, 295),
            Rect(420, 18, 429, 285),
            Rect(500, 9, 581, 18),
            Rect(500, 121, 581, 130),
            Rect(582, 20, 591, 120),
            Rect(497, 286, 581, 295),
            Rect(582, 180, 591, 285),
            Rect(630, 5, 701, 14),
            Rect(630, 121, 701, 130),
            Rect(702, 16, 711, 120)
        )
    }

    @Test
    fun `onDraw - horizontal reversed GridLayoutManager, multiple rows`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10)
        ).assertEquals(
            Rect(333, 126, 420, 136),
            Rect(323, 16, 333, 136),
            Rect(327, 168, 337, 281),
            Rect(175, 123, 286, 133),
            Rect(165, 18, 175, 133),
            Rect(170, 172, 180, 290),
            Rect(15, 128, 126, 138)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(500, 20, 580, 120).margins(2, 2, 2, 1),
            view(500, 180, 580, 280).margins(1, 3, 2, 7),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(498, 17, 582, 18),
            Rect(498, 121, 582, 122),
            Rect(497, 17, 498, 122),
            Rect(499, 287, 582, 288),
            Rect(498, 177, 499, 288),
            Rect(333, 15, 420, 16),
            Rect(333, 126, 420, 127),
            Rect(332, 15, 333, 127),
            Rect(337, 281, 423, 282),
            Rect(336, 168, 337, 282),
            Rect(175, 17, 286, 18),
            Rect(175, 123, 286, 124),
            Rect(174, 17, 175, 124),
            Rect(180, 290, 281, 291),
            Rect(179, 172, 180, 291),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            false,
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10)
        ).assertEquals(
            Rect(335, 9, 419, 16),
            Rect(335, 126, 419, 133),
            Rect(420, 17, 427, 124),
            Rect(326, 17, 333, 124),
            Rect(339, 281, 422, 288),
            Rect(423, 169, 430, 279),
            Rect(330, 169, 337, 279),
            Rect(177, 11, 285, 18),
            Rect(177, 123, 285, 130),
            Rect(168, 19, 175, 121),
            Rect(182, 290, 280, 297),
            Rect(173, 173, 180, 288),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126),
            Rect(20, 290, 119, 297),
            Rect(11, 178, 18, 288)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true).withLookup(),
            false,
            view(630, 20, 700, 120).margins(2, 6, 2, 1),
            view(500, 20, 580, 120).margins(2, 2, 2, 1),
            view(500, 180, 580, 280).margins(5, 2, 2, 6),
            view(340, 20, 420, 280).margins(7, 4, 0, 6),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 280).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(629, 5, 700, 14),
            Rect(629, 121, 700, 130),
            Rect(619, 16, 628, 120),
            Rect(499, 9, 580, 18),
            Rect(499, 121, 580, 130),
            Rect(489, 20, 498, 120),
            Rect(496, 286, 580, 295),
            Rect(486, 180, 495, 285),
            Rect(334, 7, 418, 16),
            Rect(334, 286, 418, 295),
            Rect(324, 18, 333, 285),
            Rect(176, 9, 284, 18),
            Rect(176, 123, 284, 132),
            Rect(166, 20, 175, 122),
            Rect(181, 290, 279, 299),
            Rect(171, 174, 180, 289),
            Rect(16, 7, 124, 16),
            Rect(16, 288, 124, 297),
            Rect(6, 18, 15, 287)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal RTL GridLayoutManager, multiple rows`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10)
        ).assertEquals(
            Rect(333, 126, 420, 136),
            Rect(323, 16, 333, 136),
            Rect(327, 168, 337, 281),
            Rect(175, 123, 286, 133),
            Rect(165, 18, 175, 133),
            Rect(170, 172, 180, 290),
            Rect(15, 128, 126, 138)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(500, 20, 580, 120).margins(2, 2, 2, 1),
            view(500, 180, 580, 280).margins(1, 3, 2, 7),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(498, 17, 582, 18),
            Rect(498, 121, 582, 122),
            Rect(497, 17, 498, 122),
            Rect(499, 287, 582, 288),
            Rect(498, 177, 499, 288),
            Rect(333, 15, 420, 16),
            Rect(333, 126, 420, 127),
            Rect(332, 15, 333, 127),
            Rect(337, 281, 423, 282),
            Rect(336, 168, 337, 282),
            Rect(175, 17, 286, 18),
            Rect(175, 123, 286, 124),
            Rect(174, 17, 175, 124),
            Rect(180, 290, 281, 291),
            Rect(179, 172, 180, 291),
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false),
            true,
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10)
        ).assertEquals(
            Rect(335, 9, 419, 16),
            Rect(335, 126, 419, 133),
            Rect(420, 17, 427, 124),
            Rect(326, 17, 333, 124),
            Rect(339, 281, 422, 288),
            Rect(423, 169, 430, 279),
            Rect(330, 169, 337, 279),
            Rect(177, 11, 285, 18),
            Rect(177, 123, 285, 130),
            Rect(168, 19, 175, 121),
            Rect(182, 290, 280, 297),
            Rect(173, 173, 180, 288),
            Rect(17, 9, 125, 16),
            Rect(17, 128, 125, 135),
            Rect(8, 17, 15, 126),
            Rect(20, 290, 119, 297),
            Rect(11, 178, 18, 288)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, false).withLookup(),
            true,
            view(630, 20, 700, 120).margins(2, 6, 2, 1),
            view(500, 20, 580, 120).margins(2, 2, 2, 1),
            view(500, 180, 580, 280).margins(5, 2, 2, 6),
            view(340, 20, 420, 280).margins(7, 4, 0, 6),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(20, 20, 120, 280).margins(5, 4, 6, 8)
        ).assertEquals(
            Rect(629, 5, 700, 14),
            Rect(629, 121, 700, 130),
            Rect(619, 16, 628, 120),
            Rect(499, 9, 580, 18),
            Rect(499, 121, 580, 130),
            Rect(489, 20, 498, 120),
            Rect(496, 286, 580, 295),
            Rect(486, 180, 495, 285),
            Rect(334, 7, 418, 16),
            Rect(334, 286, 418, 295),
            Rect(324, 18, 333, 285),
            Rect(176, 9, 284, 18),
            Rect(176, 123, 284, 132),
            Rect(166, 20, 175, 122),
            Rect(181, 290, 279, 299),
            Rect(171, 174, 180, 289),
            Rect(16, 7, 124, 16),
            Rect(16, 288, 124, 297),
            Rect(6, 18, 15, 287)
        )
    }

    @Config(minSdk = 17)
    @Test
    fun `onDraw - horizontal reversed RTL GridLayoutManager, multiple rows`() {
        decoration().onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1)
        ).assertEquals(
            Rect(15, 128, 126, 138),
            Rect(126, 16, 136, 138),
            Rect(120, 177, 130, 290),
            Rect(175, 123, 286, 133),
            Rect(286, 18, 296, 133),
            Rect(281, 172, 291, 290),
            Rect(333, 126, 420, 136)
        )

        decoration(
            size = 1,
            tint = Color.BLACK,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1),
            view(500, 20, 580, 120).margins(2, 2, 2, 1)
        ).assertEquals(
            Rect(15, 15, 126, 16),
            Rect(15, 128, 126, 129),
            Rect(126, 15, 127, 129),
            Rect(18, 290, 120, 291),
            Rect(120, 177, 121, 291),
            Rect(175, 17, 286, 18),
            Rect(175, 123, 286, 124),
            Rect(286, 17, 287, 124),
            Rect(180, 290, 281, 291),
            Rect(281, 172, 282, 291),
            Rect(333, 15, 420, 16),
            Rect(333, 126, 420, 127),
            Rect(420, 15, 421, 127),
            Rect(337, 281, 423, 282),
            Rect(423, 168, 424, 282),
            Rect(498, 17, 582, 18),
            Rect(498, 121, 582, 122)
        )

        decoration(
            size = 7,
            insetStart = 1,
            insetEnd = 2,
            isFirstDividerVisible = true,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true),
            true,
            view(20, 20, 120, 120).margins(5, 4, 6, 8),
            view(20, 180, 120, 280).margins(2, 3, 0, 10),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 120).margins(7, 4, 0, 6),
            view(340, 180, 420, 280).margins(3, 12, 3, 1)
        ).assertEquals(
            Rect(16, 9, 124, 16),
            Rect(16, 128, 124, 135),
            Rect(8, 17, 15, 126),
            Rect(126, 17, 133, 126),
            Rect(19, 290, 118, 297),
            Rect(11, 178, 18, 288),
            Rect(120, 178, 127, 288),
            Rect(176, 11, 284, 18),
            Rect(176, 123, 284, 130),
            Rect(286, 19, 293, 121),
            Rect(181, 290, 279, 297),
            Rect(281, 173, 288, 288),
            Rect(334, 9, 418, 16),
            Rect(334, 126, 418, 133),
            Rect(420, 17, 427, 124),
            Rect(338, 281, 421, 288),
            Rect(423, 169, 430, 279)
        )

        decoration(
            size = 9,
            insetStart = 2,
            insetEnd = 1,
            isFirstDividerVisible = false,
            isLastDividerVisible = true,
            areSideDividersVisible = true
        ).onDraw(
            gridLayoutManager(2, Orientation.HORIZONTAL, true).withLookup(),
            true,
            view(20, 20, 120, 280).margins(5, 4, 6, 8),
            view(180, 20, 280, 120).margins(5, 2, 6, 3),
            view(180, 180, 280, 280).margins(0, 8, 1, 10),
            view(340, 20, 420, 280).margins(7, 4, 0, 6),
            view(500, 20, 580, 120).margins(2, 2, 2, 1),
            view(500, 180, 580, 280).margins(5, 2, 2, 6),
            view(630, 20, 700, 120).margins(2, 6, 2, 1)
        ).assertEquals(
            Rect(17, 7, 125, 16),
            Rect(17, 288, 125, 297),
            Rect(126, 18, 135, 287),
            Rect(177, 9, 285, 18),
            Rect(177, 123, 285, 132),
            Rect(286, 20, 295, 122),
            Rect(182, 290, 280, 299),
            Rect(281, 174, 290, 289),
            Rect(335, 7, 419, 16),
            Rect(335, 286, 419, 295),
            Rect(420, 18, 429, 285),
            Rect(500, 9, 581, 18),
            Rect(500, 121, 581, 130),
            Rect(582, 20, 591, 120),
            Rect(497, 286, 581, 295),
            Rect(582, 180, 591, 285),
            Rect(630, 5, 701, 14),
            Rect(630, 121, 701, 130),
            Rect(702, 16, 711, 120)
        )
    }

    private fun decoration(
        @Px size: Int = 10,
        @ColorInt color: Int = Color.RED,
        @ColorInt tint: Int? = null,
        @Px insetStart: Int = 0,
        @Px insetEnd: Int = 0,
        isFirstDividerVisible: Boolean = false,
        isLastDividerVisible: Boolean = false,
        areSideDividersVisible: Boolean = false
    ): DividerItemDecoration = DividerItemDecoration(
        asSpace = false,
        drawableProvider = DrawableProviderImpl(ColorDrawable(color)),
        tintProvider = TintProviderImpl(tint),
        sizeProvider = SizeProviderImpl(context, size),
        insetProvider = InsetProviderImpl(dividerInsetStart = insetStart, dividerInsetEnd = insetEnd),
        offsetProvider = DividerOffsetProviderImpl(areSideDividersVisible),
        visibilityProvider = VisibilityProviderImpl(
            isFirstDividerVisible = isFirstDividerVisible,
            isLastDividerVisible = isLastDividerVisible,
            areSideDividersVisible = areSideDividersVisible
        )
    )

    private fun view(left: Int, top: Int, right: Int, bottom: Int): View = View(context).apply {
        layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        bounds(left, top, right, bottom)
    }

    private fun RecyclerView.ItemDecoration.getItemOffsets(
        layoutManager: RecyclerView.LayoutManager,
        isRTL: Boolean,
        itemCount: Int
    ): List<Rect> = getItemOffsets(
        layoutManager = layoutManager,
        isRTL = isRTL,
        views = Array(itemCount) { View(context) }
    )

    private fun GridLayoutManager.withLookup(): GridLayoutManager = apply {
        spanSizeLookup = DummySpanSizeLookup()
    }

    private class DummySpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int = if (position % 3 == 0) 2 else 1
    }
}
