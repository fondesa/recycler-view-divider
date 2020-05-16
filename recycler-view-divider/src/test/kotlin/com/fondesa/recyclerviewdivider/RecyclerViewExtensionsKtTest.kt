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
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Tests of RecyclerViewExtensions.kt file.
 */
class RecyclerViewExtensionsKtTest {

    @Test
    fun `getChildAdapterPositionOrNull - getChildAdapterPosition returns valid position - returns original position`() {
        val view = mock<View>()
        val recyclerView = mock<RecyclerView> {
            on(it.getChildAdapterPosition(view)) doReturn 3
        }

        assertEquals(3, recyclerView.getChildAdapterPositionOrNull(view))
    }

    @Test
    fun `getChildAdapterPositionOrNull - getChildAdapterPosition returns NO_POSITION - returns null`() {
        val view = mock<View>()
        val recyclerView = mock<RecyclerView> {
            on(it.getChildAdapterPosition(view)) doReturn RecyclerView.NO_POSITION
        }

        assertNull(recyclerView.getChildAdapterPositionOrNull(view))
    }

    @Test
    fun `forEachItem - all children with adapter position - action executed for each child`() {
        val firstChild = mock<View>()
        val secondChild = mock<View>()
        val thirdChild = mock<View>()
        val recyclerView = mock<RecyclerView> {
            on(it.childCount) doReturn 3
            on(it.getChildAt(0)) doReturn firstChild
            on(it.getChildAt(1)) doReturn secondChild
            on(it.getChildAt(2)) doReturn thirdChild
            on(it.getChildAdapterPosition(firstChild)) doReturn 10
            on(it.getChildAdapterPosition(secondChild)) doReturn 11
            on(it.getChildAdapterPosition(thirdChild)) doReturn 12
        }
        val action = mock<Function2<Int, View, Unit>>()

        recyclerView.forEachItem(action)

        inOrder(action) {
            verify(action).invoke(10, firstChild)
            verify(action).invoke(11, secondChild)
            verify(action).invoke(12, thirdChild)
        }
        verifyNoMoreInteractions(action)
    }

    @Test
    fun `forEachItem - some children without adapter position - action executed only for children with adapter position`() {
        val firstChild = mock<View>()
        val secondChild = mock<View>()
        val thirdChild = mock<View>()
        val fourthChild = mock<View>()
        val recyclerView = mock<RecyclerView> {
            on(it.childCount) doReturn 4
            on(it.getChildAt(0)) doReturn firstChild
            on(it.getChildAt(1)) doReturn secondChild
            on(it.getChildAt(2)) doReturn thirdChild
            on(it.getChildAt(3)) doReturn fourthChild
            on(it.getChildAdapterPosition(firstChild)) doReturn 10
            on(it.getChildAdapterPosition(secondChild)) doReturn RecyclerView.NO_POSITION
            on(it.getChildAdapterPosition(thirdChild)) doReturn 12
            on(it.getChildAdapterPosition(fourthChild)) doReturn RecyclerView.NO_POSITION
        }
        val action = mock<Function2<Int, View, Unit>>()

        recyclerView.forEachItem(action)

        inOrder(action) {
            verify(action).invoke(10, firstChild)
            verify(action).invoke(12, thirdChild)
        }
        verifyNoMoreInteractions(action)
    }
}
