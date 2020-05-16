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

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of DrawWithBounds.kt file.
 */
@RunWith(AndroidJUnit4::class)
class DrawWithBoundsKtTest {

    @Test
    fun `drawWithBounds - drawable drawn with the given bounds on the given canvas`() {
        val canvas = Canvas()
        val drawable = ColorDrawable(Color.RED).apply {
            bounds = Rect(4, 5, 6, 7)
        }

        drawable.drawWithBounds(canvas = canvas, left = 4, top = 5, right = 6, bottom = 7)
    }
}
