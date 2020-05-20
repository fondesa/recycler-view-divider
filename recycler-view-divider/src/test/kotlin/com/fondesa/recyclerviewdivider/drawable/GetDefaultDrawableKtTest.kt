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

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.assertEqualDrawables
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of GetDefaultDrawable.kt file.
 */
@RunWith(AndroidJUnit4::class)
class GetDefaultDrawableKtTest {
    private lateinit var scenario: ActivityScenario<ThemeTestActivity>

    @After
    fun destroyScenario() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun `transparentDrawable - returns transparent ColorDrawable`() {
        val drawable = transparentDrawable()

        assertEqualDrawables(transparentDrawable(), drawable)
    }

    @Test
    fun `getThemeDrawable - no attrs in the theme - returns null`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)

        val drawable = scenario.letActivity { it.getThemeDrawable() }

        assertNull(drawable)
    }

    @Test
    fun `getThemeDrawable - listDivider in theme - returns listDivider value`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyAndroidListDivider)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_androidListDivider)

        val drawable = scenario.letActivity { it.getThemeDrawable() }

        assertNotNull(drawable)
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
    }

    @Test
    fun `getThemeDrawable - recyclerViewDividerDrawable in theme - returns recyclerViewDividerDrawable value`() {
        scenario = launchThemeActivity(R.style.TestTheme_OnlyDrawable)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)

        val drawable = scenario.letActivity { it.getThemeDrawable() }

        assertNotNull(drawable)
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
    }

    @Test
    fun `getThemeDrawable - listDivider and recyclerViewDividerDrawable in theme - returns recyclerViewDividerDrawable value`() {
        scenario = launchThemeActivity(R.style.TestTheme_AndroidListDividerAndDrawable)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)

        val drawable = scenario.letActivity { it.getThemeDrawable() }

        assertNotNull(drawable)
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
    }
}
