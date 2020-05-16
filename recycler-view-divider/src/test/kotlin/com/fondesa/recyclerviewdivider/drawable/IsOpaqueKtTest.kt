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

package com.fondesa.recyclerviewdivider.drawable

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of IsOpaque.kt file.
 */
@RunWith(AndroidJUnit4::class)
class IsOpaqueKtTest {

    @Test
    fun `isOpaque - transparent color - returns false`() {
        val drawable = ColorDrawable(Color.TRANSPARENT)

        assertFalse(drawable.isOpaque)
    }

    @Test
    fun `isOpaque - partially transparent color - returns false`() {
        val drawable = ColorDrawable(0x55FF139F)

        assertFalse(drawable.isOpaque)
    }

    @Test
    fun `isOpaque - not transparent color - returns true`() {
        val drawable = ColorDrawable(Color.RED)

        assertTrue(drawable.isOpaque)
    }
}
