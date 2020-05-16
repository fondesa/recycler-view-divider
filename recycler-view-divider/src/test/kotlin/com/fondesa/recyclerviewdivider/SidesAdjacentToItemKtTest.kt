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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.test.staggeredLayoutManager
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of SidesAdjacentToItem.kt file.
 */
class SidesAdjacentToItemKtTest {

    @Test
    fun `sidesAdjacentToItem - vertical LTR grid, 1 column, without fullSpan`() {
        mapOf(
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - vertical RTL grid, 1 column, without fullSpan`() {
        mapOf(
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - vertical LTR grid, 1 column, with fullSpan`() {
        mapOf(
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, true) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - vertical RTL grid, 1 column, with fullSpan`() {
        mapOf(
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START, Side.END),
            view(0, true) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - vertical LTR grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            view(0, false) to sidesOf(Side.START),
            view(1, false) to sidesOf(Side.END),
            view(0, false) to sidesOf(Side.START),
            view(1, false) to sidesOf(Side.END),
            view(0, false) to sidesOf(Side.START)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - vertical RTL grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            view(0, false) to sidesOf(Side.END),
            view(1, false) to sidesOf(Side.START),
            view(0, false) to sidesOf(Side.END),
            view(1, false) to sidesOf(Side.START),
            view(0, false) to sidesOf(Side.END)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - vertical LTR grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START),
            view(1, false) to sidesOf(Side.END),
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START),
            view(1, false) to sidesOf(Side.END)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - vertical RTL grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.END),
            view(1, false) to sidesOf(Side.START),
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.END),
            view(1, false) to sidesOf(Side.START)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - vertical LTR grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.START),
            view(0, false) to sidesOf(Side.START),
            view(1, false) to sidesOf(Side.END),
            view(1, false) to sidesOf(Side.END)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - vertical RTL grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.START, Side.END),
            view(0, false) to sidesOf(Side.END),
            view(0, false) to sidesOf(Side.END),
            view(1, false) to sidesOf(Side.START),
            view(1, false) to sidesOf(Side.START)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal LTR grid, 1 column, without fullSpan`() {
        mapOf(
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal RTL grid, 1 column, without fullSpan`() {
        mapOf(
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal LTR grid, 1 column, with fullSpan`() {
        mapOf(
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal RTL grid, 1 column, with fullSpan`() {
        mapOf(
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal LTR grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal RTL grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal LTR grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal RTL grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal LTR grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToItem - horizontal RTL grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            view(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            view(0, false) to sidesOf(Side.TOP),
            view(0, false) to sidesOf(Side.TOP),
            view(1, false) to sidesOf(Side.BOTTOM),
            view(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, layoutOrientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    private fun Map<View, Sides>.assertValid(spanCount: Int, layoutOrientation: Orientation, layoutRightToLeft: Boolean) {
        val layoutManager = staggeredLayoutManager(spanCount, layoutOrientation)
        forEach { view, expectedSides ->
            val actualSides = layoutManager.sidesAdjacentToItem(itemView = view, layoutRightToLeft = layoutRightToLeft)
            assertEquals(expectedSides, actualSides)
        }
    }

    private fun view(spanIndex: Int, isFullSpan: Boolean): View {
        val params = mock<StaggeredGridLayoutManager.LayoutParams> {
            on(it.spanIndex) doReturn spanIndex
            on(it.isFullSpan) doReturn isFullSpan
        }
        return mock {
            on(it.layoutParams) doReturn params
        }
    }

    private fun sidesOf(first: Side, vararg others: Side): Sides = sidesOf().apply {
        this += first
        this += others
    }
}
