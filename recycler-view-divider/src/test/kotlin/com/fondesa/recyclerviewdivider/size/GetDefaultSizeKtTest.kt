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

package com.fondesa.recyclerviewdivider.size

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.Px
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.pxFromSize
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import com.fondesa.recyclerviewdivider.test.resources
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy

/**
 * Tests of GetDefaultSize.kt file.
 */
@RunWith(AndroidJUnit4::class)
class GetDefaultSizeKtTest {
    private lateinit var scenario: ActivityScenario<ThemeTestActivity>

    @After
    fun destroyScenario() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun `getThemeSize - no attrs - returns null`() {
        scenario = launchThemeActivity(R.style.TestTheme)

        @Px val size = scenario.letActivity { it.getThemeSize() }

        assertNull(size)
    }

    @Test
    fun `getThemeSize - recyclerViewDividerSize in theme - returns recyclerViewDividerSize value`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlySize)
        @Px val expectedSize = resources.pxFromSize(22, TypedValue.COMPLEX_UNIT_DIP)

        @Px val size = scenario.letActivity { it.getThemeSize() }

        assertEquals(expectedSize, size)
    }

    @Test
    fun `getDefaultSize - returns 1dp`() {
        // Changes the device density to be sure the px are converted from dp instead returning a raw px value.
        val resources = spy(resources) {
            on(it.displayMetrics) doReturn DisplayMetrics().apply { density = 2f }
        }
        val context = spy(context) {
            on(it.resources) doReturn resources
        }
        @Px val expectedSize = resources.pxFromSize(1, TypedValue.COMPLEX_UNIT_DIP)

        @Px val size = context.getDefaultSize()

        assertEquals(expectedSize, size)
    }
}
