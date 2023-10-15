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

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.resources
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy

/**
 * Tests of PxFromSize.kt file.
 */
@RunWith(AndroidJUnit4::class)
class PxFromSizeKtTest {

    @Test
    fun `pxFromSize - size in px - returns input value`() {
        assertEquals(5, resources.pxFromSize(5, TypedValue.COMPLEX_UNIT_PX))
        assertEquals(0, resources.pxFromSize(0, TypedValue.COMPLEX_UNIT_PX))
        assertEquals(132, resources.pxFromSize(132, TypedValue.COMPLEX_UNIT_PX))
    }

    @Test
    fun `pxFromSize - size in dp - returns input value converted to px`() {
        val spiedResources = spy(resources) {
            on(it.displayMetrics) doReturn DisplayMetrics().apply { density = 2f }
        }
        assertEquals(10, spiedResources.pxFromSize(5, TypedValue.COMPLEX_UNIT_DIP))
        assertEquals(0, spiedResources.pxFromSize(0, TypedValue.COMPLEX_UNIT_DIP))
        assertEquals(264, spiedResources.pxFromSize(132, TypedValue.COMPLEX_UNIT_DIP))
    }

    @Test
    fun `pxFromSize - size in sp - returns input value converted to sp`() {
        @Suppress("DEPRECATION") val spiedResources = spy(resources) {
            on(it.displayMetrics) doReturn DisplayMetrics().apply { scaledDensity = 2f }
        }
        assertEquals(10, spiedResources.pxFromSize(5, TypedValue.COMPLEX_UNIT_SP))
        assertEquals(0, spiedResources.pxFromSize(0, TypedValue.COMPLEX_UNIT_SP))
        assertEquals(264, spiedResources.pxFromSize(132, TypedValue.COMPLEX_UNIT_SP))
    }
}
