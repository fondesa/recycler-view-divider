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

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of SidesAdjacentToCell.kt file.
 */
class SidesAdjacentToCellKtTest {

    @Test
    fun `sidesAdjacentToCell - vertical LTR grid, 1 column, without fullSpan`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, orientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - vertical RTL grid, 1 column, without fullSpan`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, orientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - vertical LTR grid, 1 column, with fullSpan`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, orientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - vertical RTL grid, 1 column, with fullSpan`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END)
        ).assertValid(spanCount = 1, orientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - vertical LTR grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.START),
            StaggeredCell(1, false) to sidesOf(Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START),
            StaggeredCell(1, false) to sidesOf(Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START)
        ).assertValid(spanCount = 2, orientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - vertical RTL grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.END),
            StaggeredCell(1, false) to sidesOf(Side.START),
            StaggeredCell(0, false) to sidesOf(Side.END),
            StaggeredCell(1, false) to sidesOf(Side.START),
            StaggeredCell(0, false) to sidesOf(Side.END)
        ).assertValid(spanCount = 2, orientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - vertical LTR grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START),
            StaggeredCell(1, false) to sidesOf(Side.END),
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START),
            StaggeredCell(1, false) to sidesOf(Side.END)
        ).assertValid(spanCount = 2, orientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - vertical RTL grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.END),
            StaggeredCell(1, false) to sidesOf(Side.START),
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.END),
            StaggeredCell(1, false) to sidesOf(Side.START)
        ).assertValid(spanCount = 2, orientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - vertical LTR grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.START),
            StaggeredCell(0, false) to sidesOf(Side.START),
            StaggeredCell(1, false) to sidesOf(Side.END),
            StaggeredCell(1, false) to sidesOf(Side.END)
        ).assertValid(spanCount = 2, orientation = Orientation.VERTICAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - vertical RTL grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.START, Side.END),
            StaggeredCell(0, false) to sidesOf(Side.END),
            StaggeredCell(0, false) to sidesOf(Side.END),
            StaggeredCell(1, false) to sidesOf(Side.START),
            StaggeredCell(1, false) to sidesOf(Side.START)
        ).assertValid(spanCount = 2, orientation = Orientation.VERTICAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal LTR grid, 1 column, without fullSpan`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, orientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal RTL grid, 1 column, without fullSpan`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, orientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal LTR grid, 1 column, with fullSpan`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, orientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal RTL grid, 1 column, with fullSpan`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM)
        ).assertValid(spanCount = 1, orientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal LTR grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP)
        ).assertValid(spanCount = 2, orientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal RTL grid, multiple columns, without fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP)
        ).assertValid(spanCount = 2, orientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal LTR grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, orientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal RTL grid, multiple columns, with fullSpan, without white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, orientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal LTR grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, orientation = Orientation.HORIZONTAL, layoutRightToLeft = false)
    }

    @Test
    fun `sidesAdjacentToCell - horizontal RTL grid, multiple columns, with fullSpan, with white spaces`() {
        mapOf(
            StaggeredCell(0, true) to sidesOf(Side.TOP, Side.BOTTOM),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(0, false) to sidesOf(Side.TOP),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM),
            StaggeredCell(1, false) to sidesOf(Side.BOTTOM)
        ).assertValid(spanCount = 2, orientation = Orientation.HORIZONTAL, layoutRightToLeft = true)
    }

    private fun Map<StaggeredCell, Sides>.assertValid(spanCount: Int, orientation: Orientation, layoutRightToLeft: Boolean) {
        val horizontalDirection = if (layoutRightToLeft) {
            LayoutDirection.Horizontal.RIGHT_TO_LEFT
        } else {
            LayoutDirection.Horizontal.LEFT_TO_RIGHT
        }
        val grid = StaggeredGrid(
            spanCount = spanCount,
            orientation = orientation,
            layoutDirection = LayoutDirection(horizontal = horizontalDirection, vertical = LayoutDirection.Vertical.TOP_TO_BOTTOM)
        )
        forEach { cell, expectedSides ->
            val actualSides = grid.sidesAdjacentToCell(cell)
            assertEquals(expectedSides, actualSides)
        }
    }

    private fun sidesOf(first: Side, vararg others: Side): Sides = sidesOf().apply {
        this += first
        this += others
    }
}
