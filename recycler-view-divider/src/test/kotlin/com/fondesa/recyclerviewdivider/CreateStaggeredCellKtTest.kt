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

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Tests of CreateStaggeredCell.kt file.
 */
class CreateStaggeredCellKtTest {

    @Test
    fun `staggeredCell - view with null layout params - throws exception`() {
        val view = mock<View> {
            on(it.layoutParams) doReturn null
        }

        assertThrows(ClassCastException::class.java) { view.staggeredCell() }
    }

    @Test
    fun `staggeredCell - view without staggered layout params - throws exception`() {
        val params = mock<ViewGroup.LayoutParams>()
        val view = mock<View> {
            on(it.layoutParams) doReturn params
        }

        assertThrows(ClassCastException::class.java) { view.staggeredCell() }
    }

    @Test
    fun `staggeredCell - view with staggered layout params, not full span - returns cell not full span`() {
        val params = mock<StaggeredGridLayoutManager.LayoutParams> {
            on(it.spanIndex) doReturn 2
            on(it.isFullSpan) doReturn false
        }
        val view = mock<View> {
            on(it.layoutParams) doReturn params
        }

        val cell = view.staggeredCell()

        assertEquals(StaggeredCell(2, false), cell)
    }

    @Test
    fun `staggeredCell - view with staggered layout params, full span - returns cell full span`() {
        val params = mock<StaggeredGridLayoutManager.LayoutParams> {
            on(it.spanIndex) doReturn 0
            on(it.isFullSpan) doReturn true
        }
        val view = mock<View> {
            on(it.layoutParams) doReturn params
        }

        val cell = view.staggeredCell()

        assertEquals(StaggeredCell(0, true), cell)
    }
}
