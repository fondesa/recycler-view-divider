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

package com.fondesa.recyclerviewdivider.space

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of GetDefaultAsSpace.kt file.
 */
@RunWith(AndroidJUnit4::class)
class GetDefaultAsSpaceKtTest {
    private lateinit var scenario: ActivityScenario<ThemeTestActivity>

    @After
    fun destroyScenario() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun `getThemeSpaceOrDefault - no attrs - returns false`() {
        scenario = launchThemeActivity(R.style.TestTheme)

        val asSpace = scenario.letActivity { it.getThemeAsSpaceOrDefault() }

        assertFalse(asSpace)
    }

    @Test
    fun `getThemeSpaceOrDefault - recyclerViewDividerAsSpace in theme with value true - returns true`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyAsSpaceTrue)

        val asSpace = scenario.letActivity { it.getThemeAsSpaceOrDefault() }

        assertTrue(asSpace)
    }

    @Test
    fun `getThemeSpaceOrDefault - recyclerViewDividerAsSpace in theme with value false - returns false`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyAsSpaceFalse)

        val asSpace = scenario.letActivity { it.getThemeAsSpaceOrDefault() }

        assertFalse(asSpace)
    }
}
