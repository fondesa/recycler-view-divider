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
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of NormalizedOffsetFromSize.kt file.
 */
class NormalizedOffsetFromSizeKtTest {

    @Test
    fun `getOffsetFromSize - side dividers visible - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 10, 5, 0, true).assertEquals(2)
        normalizedOffsetFromSize(Side.BOTTOM, 10, 5, 0, true).assertEquals(2)
        normalizedOffsetFromSize(Side.START, 10, 5, 1, true).assertEquals(8)
        normalizedOffsetFromSize(Side.TOP, 10, 5, 1, true).assertEquals(8)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, odd size - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 11, 5, 0, true).assertEquals(2)
        normalizedOffsetFromSize(Side.BOTTOM, 11, 5, 0, true).assertEquals(2)
        normalizedOffsetFromSize(Side.START, 11, 5, 1, true).assertEquals(9)
        normalizedOffsetFromSize(Side.TOP, 11, 5, 1, true).assertEquals(9)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, 1px size - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 1, 5, 0, true).assertEquals(0)
        normalizedOffsetFromSize(Side.BOTTOM, 1, 5, 0, true).assertEquals(0)
        normalizedOffsetFromSize(Side.START, 1, 5, 1, true).assertEquals(1)
        normalizedOffsetFromSize(Side.TOP, 1, 5, 1, true).assertEquals(1)
    }

    @Test
    fun `getOffsetFromSize - side dividers visible, point-five size - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 11, 2, 0, true).assertEquals(6)
        normalizedOffsetFromSize(Side.BOTTOM, 11, 2, 0, true).assertEquals(6)
        normalizedOffsetFromSize(Side.START, 11, 2, 1, true).assertEquals(5)
        normalizedOffsetFromSize(Side.TOP, 11, 2, 1, true).assertEquals(5)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 10, 5, 0, false).assertEquals(8)
        normalizedOffsetFromSize(Side.BOTTOM, 10, 5, 0, false).assertEquals(8)
        normalizedOffsetFromSize(Side.START, 10, 5, 1, false).assertEquals(2)
        normalizedOffsetFromSize(Side.TOP, 10, 5, 1, false).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, odd size - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 11, 5, 0, false).assertEquals(9)
        normalizedOffsetFromSize(Side.BOTTOM, 11, 5, 0, false).assertEquals(9)
        normalizedOffsetFromSize(Side.START, 11, 5, 1, false).assertEquals(2)
        normalizedOffsetFromSize(Side.TOP, 11, 5, 1, false).assertEquals(2)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, 1px size - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 1, 5, 0, false).assertEquals(1)
        normalizedOffsetFromSize(Side.BOTTOM, 1, 5, 0, false).assertEquals(1)
        normalizedOffsetFromSize(Side.START, 1, 5, 1, false).assertEquals(0)
        normalizedOffsetFromSize(Side.TOP, 1, 5, 1, false).assertEquals(0)
    }

    @Test
    fun `getOffsetFromSize - side dividers not visible, point-five size - returns balanced size`() {
        normalizedOffsetFromSize(Side.END, 11, 2, 0, false).assertEquals(6)
        normalizedOffsetFromSize(Side.BOTTOM, 11, 2, 0, false).assertEquals(6)
        normalizedOffsetFromSize(Side.START, 11, 2, 1, false).assertEquals(5)
        normalizedOffsetFromSize(Side.TOP, 11, 2, 1, false).assertEquals(5)
    }

    private fun Int.assertEquals(@Px expected: Int) {
        assertEquals(expected, this)
    }
}
