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

package com.fondesa.recyclerviewdivider.tint

import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of GetDefaultTintColor.kt file.
 */
@RunWith(AndroidJUnit4::class)
class GetDefaultTintColorKtTest {
    private lateinit var scenario: ActivityScenario<ThemeTestActivity>

    @After
    fun destroyScenario() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun `getThemeTintColor - no attrs - returns null`() {
        scenario = launchThemeActivity(R.style.TestTheme)

        @ColorInt val tintColor = scenario.letActivity { it.getThemeTintColor() }

        assertNull(tintColor)
    }

    @Test
    fun `getThemeTintColor - recyclerViewDividerTint in theme - returns recyclerViewDividerTint value`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyTint)
        @ColorInt val expectedTintColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerTint)

        @ColorInt val tintColor = scenario.letActivity { it.getThemeTintColor() }

        assertEquals(expectedTintColor, tintColor)
    }
}
