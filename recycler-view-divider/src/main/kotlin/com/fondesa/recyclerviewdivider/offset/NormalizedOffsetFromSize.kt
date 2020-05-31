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

package com.fondesa.recyclerviewdivider.offset

import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.Side
import kotlin.math.roundToInt

@Px
internal fun normalizedOffsetFromSize(
    dividerSide: Side,
    @Px size: Int,
    spanCount: Int,
    spanIndex: Int,
    areSideDividersVisible: Boolean
): Int {
    val multiplier = size.toFloat() / spanCount
    // The calculation changes if the side dividers are visible because the grid starts and ends with two offsets which should be
    // added only to the first and the last cell for each line.
    val rawOffset = if (areSideDividersVisible) {
        rawOffsetWithSideDividers(dividerSide, multiplier, spanCount, spanIndex)
    } else {
        rawOffsetWithoutSideDividers(dividerSide, multiplier, spanCount, spanIndex)
    }
    return normalizeOffset(dividerSide, rawOffset)
}

private fun normalizeOffset(dividerSide: Side, rawOffset: Float): Int = if (rawOffset.decimalPartIsPointFive) {
    // If the decimal part of the offset is .5, the method roundToInt() rounds the float to the smaller integer.
    // So, the method roundToInt() fails in an example like the following:
    // - the grid is vertical with two column
    // - "areSideDividersVisible" is false
    // - the divider size is 11
    // The divider size would have been split in two offsets of 5.5, rounded to 5.
    // The resulting total offset would have a width of 10 instead of 11.
    // That's why the two raw offsets are normalized to 5 and 6.
    if (dividerSide == Side.TOP || dividerSide == Side.START) rawOffset.toInt() else rawOffset.toInt() + 1
} else {
    rawOffset.roundToInt()
}

private fun rawOffsetWithSideDividers(
    dividerSide: Side,
    multiplier: Float,
    spanCount: Int,
    spanIndex: Int
): Float {
    val dividersInLine = spanCount + 1
    val cellSideOffset = multiplier * dividersInLine
    return when (dividerSide) {
        Side.START, Side.TOP -> rawOffsetBeforeWithSideDividers(spanIndex, multiplier, spanCount)
        Side.END, Side.BOTTOM -> rawOffsetAfterWithSideDividers(spanIndex, multiplier, spanCount, cellSideOffset)
    }
}

private fun rawOffsetWithoutSideDividers(dividerSide: Side, multiplier: Float, spanCount: Int, spanIndex: Int): Float {
    val dividersInLine = spanCount - 1
    val cellSideOffset = multiplier * dividersInLine
    return when (dividerSide) {
        Side.START, Side.TOP -> rawOffsetBeforeWithoutSideDividers(spanIndex, multiplier)
        Side.END, Side.BOTTOM -> rawOffsetAfterWithoutSideDividers(spanIndex, multiplier, cellSideOffset)
    }
}

private fun rawOffsetBeforeWithSideDividers(columnIndex: Int, multiplier: Float, spanCount: Int): Float =
    multiplier * (spanCount - columnIndex)

private fun rawOffsetAfterWithSideDividers(columnIndex: Int, multiplier: Float, spanCount: Int, totalSideOffsetForCell: Float): Float {
    val before = rawOffsetBeforeWithSideDividers(columnIndex, multiplier, spanCount)
    return totalSideOffsetForCell - before
}

private fun rawOffsetBeforeWithoutSideDividers(columnIndex: Int, multiplier: Float): Float = multiplier * columnIndex

private fun rawOffsetAfterWithoutSideDividers(columnIndex: Int, multiplier: Float, totalSideOffsetForCell: Float): Float {
    val before = rawOffsetBeforeWithoutSideDividers(columnIndex, multiplier)
    return totalSideOffsetForCell - before
}

private val Float.decimalPartIsPointFive: Boolean
    get() {
        // The method .toInt() trims the decimal part leaving only the integer one.
        // Multiplying an integer by two returns an even number.
        // Summing one to it, returns an odd number.
        // Dividing the odd number by two, returns a .5 number.
        return this == (toInt() * 2 + 1).toFloat() / 2
    }
