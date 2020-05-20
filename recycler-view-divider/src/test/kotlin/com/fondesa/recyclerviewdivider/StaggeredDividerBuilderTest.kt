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

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.drawable.transparentDrawable
import com.fondesa.recyclerviewdivider.log.RecyclerViewDividerLog
import com.fondesa.recyclerviewdivider.size.getDefaultSize
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.assertEqualDrawables
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import com.fondesa.recyclerviewdivider.test.resources
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of [StaggeredDividerBuilder].
 */
@RunWith(AndroidJUnit4::class)
class StaggeredDividerBuilderTest {
    private val logger = mock<RecyclerViewDividerLog.Logger>()
    private lateinit var scenario: ActivityScenario<ThemeTestActivity>

    @Before
    fun mockLogger() {
        RecyclerViewDividerLog.use(logger)
    }

    @After
    fun destroyScenario() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun `build - returns new StaggeredDividerItemDecoration`() {
        val decoration = staggeredDividerBuilder().build()

        assertTrue(decoration is StaggeredDividerItemDecoration)
        verify(logger, never()).logWarning(
            "Can't render the divider without a color. " +
                "Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" in the theme or set a color " +
                "in this StaggeredDividerBuilder."
        )
    }

    @Test
    fun `build - asSpace not invoked, recyclerViewDividerAsSpace not in theme - returns decoration with asSpace false`() {
        val decoration = staggeredDividerBuilder().build()

        assertFalse(decoration.asSpace)
        verify(logger).logWarning(
            "Can't ensure the correct rendering of a divider drawable which isn't a solid color in a StaggeredGridLayoutManager."
        )
    }

    @Test
    fun `build - asSpace not invoked, recyclerViewDividerAsSpace true in theme - returns decoration with asSpace true`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder().build()

        assertTrue(decoration.asSpace)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - asSpace invoked - returns decoration with asSpace true`() {
        val decoration = staggeredDividerBuilder()
            .asSpace()
            .build()

        assertTrue(decoration.asSpace)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - colorRes invoked - returns decoration with color`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder()
            .colorRes(R.color.test_recyclerViewDividerDrawable)
            .build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(ColorDrawable(ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)), drawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color invoked - returns decoration with color`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder()
            .color(Color.RED)
            .build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(ColorDrawable(Color.RED), drawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color not set, solid opaque color in theme, asSpace false - returns decoration with drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_All_NotAsSpace)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)

        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color not set, solid opaque color in theme, asSpace true - returns decoration with drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)

        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color not set, drawable not solid color in theme, asSpace false - returns decoration with drawable and logs a warning`() {
        scenario = launchThemeActivity(R.style.TestTheme_All_NotAsSpace_NotColorDrawable)
        val expectedDrawable = ContextCompat.getDrawable(context, R.drawable.test_recycler_view_drawable_not_solid)

        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(expectedDrawable, drawable)
        verify(logger).logWarning(
            "Can't ensure the correct rendering of a divider drawable which isn't a solid color in a StaggeredGridLayoutManager."
        )
    }

    @Test
    fun `build - color not set, drawable not solid color in theme, asSpace true - returns decoration with drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_All_NotColorDrawable)
        val expectedDrawable = ContextCompat.getDrawable(context, R.drawable.test_recycler_view_drawable_not_solid)

        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(drawable, expectedDrawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color not set, alpha color in theme, asSpace false - returns decoration with drawable and logs a warning`() {
        scenario = launchThemeActivity(R.style.TestTheme_All_NotAsSpace_AlphaColor)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable_alpha)

        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
        verify(logger).logWarning(
            "Can't ensure the correct rendering of a divider color which has alpha in a StaggeredGridLayoutManager."
        )
    }

    @Test
    fun `build - color not set, alpha color in theme, asSpace true - returns decoration with drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_All_AlphaColor)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable_alpha)

        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(ColorDrawable(expectedColor), drawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color not set, not in theme, asSpace false - returns decoration with transparent drawable and logs a warning`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)
        val decoration = staggeredDividerBuilder().build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(transparentDrawable(), drawable)
        verify(logger).logWarning(
            "Can't render the divider without a color. " +
                "Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" in the theme or set a color " +
                "in this StaggeredDividerBuilder."
        )
    }

    @Test
    fun `build - color not set, not in theme, asSpace true - returns decoration with transparent drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)
        val decoration = staggeredDividerBuilder()
            .asSpace()
            .build()

        val drawable = (decoration as StaggeredDividerItemDecoration).drawable
        assertEqualDrawables(transparentDrawable(), drawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size invoked - returns decoration with size`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder()
            .size(4, sizeUnit = TypedValue.COMPLEX_UNIT_DIP)
            .build()

        @Px val providerSize = (decoration as StaggeredDividerItemDecoration).size
        assertEquals(resources.pxFromSize(4, sizeUnit = TypedValue.COMPLEX_UNIT_DIP), providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size invoked without size unit - returns decoration with size converted in px`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder()
            .size(4)
            .build()

        @Px val providerSize = (decoration as StaggeredDividerItemDecoration).size
        assertEquals(4, providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size in theme - returns decoration with size`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder().build()

        @Px val providerSize = (decoration as StaggeredDividerItemDecoration).size
        assertEquals(resources.pxFromSize(21, sizeUnit = TypedValue.COMPLEX_UNIT_DIP), providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size not set, not in theme - returns decoration with default size`() {
        val decoration = staggeredDividerBuilder().build()

        @Px val providerSize = (decoration as StaggeredDividerItemDecoration).size
        assertEquals(context.getDefaultSize(), providerSize)
        verify(logger).logWarning(
            "Can't ensure the correct rendering of a divider drawable which isn't a solid color in a StaggeredGridLayoutManager."
        )
    }

    @Test
    fun `build - hideSideDividers not invoked - returns decoration with side dividers visible`() {
        val decoration = staggeredDividerBuilder().build()

        assertTrue((decoration as StaggeredDividerItemDecoration).areSideDividersVisible)
        verify(logger).logWarning(
            "Can't ensure the correct rendering of a divider drawable which isn't a solid color in a StaggeredGridLayoutManager."
        )
    }

    @Test
    fun `build - hideSideDividers invoked - returns decoration with side dividers not visible`() {
        val decoration = staggeredDividerBuilder()
            .hideSideDividers()
            .build()

        assertFalse((decoration as StaggeredDividerItemDecoration).areSideDividersVisible)
        verify(logger).logWarning(
            "Can't ensure the correct rendering of a divider drawable which isn't a solid color in a StaggeredGridLayoutManager."
        )
    }

    @Test
    fun `build - base configuration - returns default decoration`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)

        val decoration = staggeredDividerBuilder().build()

        assertFalse((decoration as StaggeredDividerItemDecoration).asSpace)
        assertEqualDrawables(transparentDrawable(), decoration.drawable)
        assertEquals(context.getDefaultSize(), decoration.size)
        assertTrue(decoration.areSideDividersVisible)
        verify(logger).logWarning(
            "Can't render the divider without a color. " +
                "Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" in the theme or set a color " +
                "in this StaggeredDividerBuilder."
        )
    }

    @Test
    fun `build - theme configuration - returns decoration with values from theme`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder().build()

        assertTrue((decoration as StaggeredDividerItemDecoration).asSpace)
        @ColorInt val expectedDrawableColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)
        assertEqualDrawables(ColorDrawable(expectedDrawableColor), decoration.drawable)
        assertEquals(resources.pxFromSize(21, TypedValue.COMPLEX_UNIT_DIP), decoration.size)
        assertTrue(decoration.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - dynamic configuration - returns decoration with values set dynamically`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = staggeredDividerBuilder()
            .asSpace()
            .color(Color.RED)
            .size(34)
            .hideSideDividers()
            .build()

        assertTrue((decoration as StaggeredDividerItemDecoration).asSpace)
        assertEqualDrawables(ColorDrawable(Color.RED), decoration.drawable)
        assertEquals(34, decoration.size)
        assertFalse(decoration.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    private fun staggeredDividerBuilder(): StaggeredDividerBuilder = if (::scenario.isInitialized) {
        scenario.letActivity { StaggeredDividerBuilder(it) }
    } else {
        StaggeredDividerBuilder(context)
    }
}
