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

package com.fondesa.recyclerviewdivider.inset

import android.util.TypedValue
import androidx.annotation.Px
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.pxFromSize
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import com.fondesa.recyclerviewdivider.test.resources
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of GetDefaultInsets.kt file.
 */
@RunWith(AndroidJUnit4::class)
class GetDefaultInsetsKtTest {
    private lateinit var scenario: ActivityScenario<ThemeTestActivity>

    @After
    fun destroyScenario() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun `getThemeInsetStartOrDefault - no attrs - returns 0`() {
        scenario = launchThemeActivity(R.style.TestTheme)

        @Px val insetStart = scenario.letActivity { it.getThemeInsetStartOrDefault() }

        assertEquals(0, insetStart)
    }

    @Test
    fun `getThemeInsetStartOrDefault - recyclerViewDividerInsetStart in theme - returns recyclerViewDividerInsetStart value`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyInsetStart)
        @Px val expectedInsetStart = resources.pxFromSize(26, TypedValue.COMPLEX_UNIT_DIP)

        @Px val insetStart = scenario.letActivity { it.getThemeInsetStartOrDefault() }

        assertEquals(expectedInsetStart, insetStart)
    }

    @Test
    fun `getThemeInsetEndOrDefault - no attrs - returns 0`() {
        scenario = launchThemeActivity(R.style.TestTheme)

        @Px val insetEnd = scenario.letActivity { it.getThemeInsetEndOrDefault() }

        assertEquals(0, insetEnd)
    }

    @Test
    fun `getThemeInsetEndOrDefault - recyclerViewDividerInsetEnd in theme - returns recyclerViewDividerInsetEnd value`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyInsetEnd)
        @Px val expectedInsetEnd = resources.pxFromSize(47, TypedValue.COMPLEX_UNIT_DIP)

        @Px val insetEnd = scenario.letActivity { it.getThemeInsetEndOrDefault() }

        assertEquals(expectedInsetEnd, insetEnd)
    }
}
