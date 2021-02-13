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

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.cache.InMemoryGridCache
import com.fondesa.recyclerviewdivider.drawable.DrawableProvider
import com.fondesa.recyclerviewdivider.drawable.DrawableProviderImpl
import com.fondesa.recyclerviewdivider.drawable.transparentDrawable
import com.fondesa.recyclerviewdivider.inset.InsetProvider
import com.fondesa.recyclerviewdivider.inset.InsetProviderImpl
import com.fondesa.recyclerviewdivider.log.RecyclerViewDividerLog
import com.fondesa.recyclerviewdivider.offset.DividerOffsetProvider
import com.fondesa.recyclerviewdivider.size.SizeProvider
import com.fondesa.recyclerviewdivider.size.SizeProviderImpl
import com.fondesa.recyclerviewdivider.test.R
import com.fondesa.recyclerviewdivider.test.ThemeTestActivity
import com.fondesa.recyclerviewdivider.test.assertEqualDrawables
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.launchThemeActivity
import com.fondesa.recyclerviewdivider.test.letActivity
import com.fondesa.recyclerviewdivider.test.resources
import com.fondesa.recyclerviewdivider.tint.TintProvider
import com.fondesa.recyclerviewdivider.tint.TintProviderImpl
import com.fondesa.recyclerviewdivider.visibility.VisibilityProvider
import com.fondesa.recyclerviewdivider.visibility.VisibilityProviderImpl
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of [DividerBuilder].
 */
@RunWith(AndroidJUnit4::class)
class DividerBuilderTest {
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
    fun `build - returns new DividerItemDecoration`() {
        val decoration = dividerBuilder().build()

        assertTrue(decoration is DividerItemDecoration)
    }

    @Test
    fun `build - asSpace not invoked, recyclerViewDividerAsSpace not in theme - returns decoration with asSpace false`() {
        val decoration = dividerBuilder().build()

        assertFalse(decoration.asSpace)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - asSpace not invoked, recyclerViewDividerAsSpace true in theme - returns decoration with asSpace true`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder().build()

        assertTrue(decoration.asSpace)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - asSpace invoked - returns decoration with asSpace true`() {
        val decoration = dividerBuilder()
            .asSpace()
            .build()

        assertTrue(decoration.asSpace)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - colorRes invoked - returns decoration with color`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .colorRes(R.color.test_recyclerViewDividerDrawable)
            .build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(ColorDrawable(ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)), providerDrawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - color invoked - returns decoration with color`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .color(Color.RED)
            .build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(ColorDrawable(Color.RED), providerDrawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - drawableRes invoked - returns decoration with drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        val expectedDrawable = requireNotNull(ContextCompat.getDrawable(context, R.drawable.test_recycler_view_drawable_not_solid))

        val decoration = dividerBuilder()
            .drawableRes(R.drawable.test_recycler_view_drawable_not_solid)
            .build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(expectedDrawable, providerDrawable)
        verifyZeroInteractions(logger)
    }

    @Test(expected = NullPointerException::class)
    fun `build - drawableRes invoked with invalid Drawable - throws NullPointerException`() {
        val resources = mock<Resources> {
            @Suppress("DEPRECATION")
            on(it.getDrawable(any())) doReturn null
            if (Build.VERSION.SDK_INT >= 21) {
                on(it.getDrawable(any(), anyOrNull())) doReturn null
            }
        }
        val context = spy(context) {
            on(it.resources) doReturn resources
        }
        DividerBuilder(context).drawableRes(R.drawable.test_recycler_view_drawable_not_solid)
    }

    @Test
    fun `build - drawable invoked - returns decoration with drawable`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .drawable(ColorDrawable(Color.RED))
            .build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(ColorDrawable(Color.RED), providerDrawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - drawableProvider invoked - returns decoration with DrawableProvider`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        val expectedProvider = mock<DrawableProvider>()

        val decoration = dividerBuilder()
            .drawableProvider(expectedProvider)
            .build()

        val actualProvider = (decoration as DividerItemDecoration).drawableProvider
        assertEquals(expectedProvider, actualProvider)
    }

    @Test
    fun `build - drawable not set - returns decoration with drawable from theme`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        @ColorInt val expectedColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)

        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(ColorDrawable(expectedColor), providerDrawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - drawable not set, not in theme - returns decoration with transparent drawable and logs a warning`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)
        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(transparentDrawable(), providerDrawable)
        verify(logger).logWarning(
            "Can't render the divider without a color/drawable. " +
                "Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" in the theme or set a color/drawable " +
                "in this DividerBuilder."
        )
    }

    @Test
    fun `build - drawable not set, not in theme, asSpace true - returns decoration with transparent drawable and logs a warning`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)
        val decoration = dividerBuilder()
            .asSpace()
            .build()

        val provider = (decoration as DividerItemDecoration).drawableProvider
        assertTrue(provider is DrawableProviderImpl)
        val providerDrawable = (provider as DrawableProviderImpl).drawable
        assertEqualDrawables(transparentDrawable(), providerDrawable)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insetStart invoked, end inset not in theme - returns decoration with start inset and 0 end inset`() {
        val decoration = dividerBuilder()
            .insetStart(32)
            .build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(32, providerInsetStart)
        assertEquals(0, providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insetStart invoked, end inset in theme - returns decoration with start and end insets`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .insetStart(32)
            .build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(32, providerInsetStart)
        assertEquals(resources.pxFromSize(9, TypedValue.COMPLEX_UNIT_DIP), providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insetEnd invoked, start inset not in theme - returns decoration with end inset and 0 start inset`() {
        val decoration = dividerBuilder()
            .insetEnd(32)
            .build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(0, providerInsetStart)
        assertEquals(32, providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insetEnd invoked, start inset in theme - returns decoration with end and start insets`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .insetEnd(32)
            .build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(resources.pxFromSize(12, TypedValue.COMPLEX_UNIT_DIP), providerInsetStart)
        assertEquals(32, providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insets invoked - returns decoration with insets`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .insets(start = 32, end = 21)
            .build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(32, providerInsetStart)
        assertEquals(21, providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insetProvider invoked - returns decoration with InsetProvider`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        val expectedProvider = mock<InsetProvider>()

        val decoration = dividerBuilder()
            .insetProvider(expectedProvider)
            .build()

        val actualProvider = (decoration as DividerItemDecoration).insetProvider
        assertEquals(expectedProvider, actualProvider)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insets not set - returns decoration with insets from theme`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(resources.pxFromSize(12, TypedValue.COMPLEX_UNIT_DIP), providerInsetStart)
        assertEquals(resources.pxFromSize(9, TypedValue.COMPLEX_UNIT_DIP), providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - insets not set, not in theme - returns decoration with no insets`() {
        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).insetProvider
        assertTrue(provider is InsetProviderImpl)
        @Px val providerInsetStart = (provider as InsetProviderImpl).dividerInsetStart
        @Px val providerInsetEnd = provider.dividerInsetEnd
        assertEquals(0, providerInsetStart)
        assertEquals(0, providerInsetEnd)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size invoked - returns decoration with size`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .size(4, sizeUnit = TypedValue.COMPLEX_UNIT_DIP)
            .build()

        val provider = (decoration as DividerItemDecoration).sizeProvider
        assertTrue(provider is SizeProviderImpl)
        @Px val providerSize = (provider as SizeProviderImpl).dividerSize
        assertEquals(resources.pxFromSize(4, sizeUnit = TypedValue.COMPLEX_UNIT_DIP), providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size invoked without size unit - returns decoration with size in pixels`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .size(4)
            .build()

        val provider = (decoration as DividerItemDecoration).sizeProvider
        assertTrue(provider is SizeProviderImpl)
        @Px val providerSize = (provider as SizeProviderImpl).dividerSize
        assertEquals(4, providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size invoked - returns decoration with size from theme`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).sizeProvider
        assertTrue(provider is SizeProviderImpl)
        @Px val providerSize = (provider as SizeProviderImpl).dividerSize
        assertEquals(resources.pxFromSize(21, sizeUnit = TypedValue.COMPLEX_UNIT_DIP), providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - size not invoked, not in theme - returns decoration with null size`() {
        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).sizeProvider
        assertTrue(provider is SizeProviderImpl)
        @Px val providerSize = (provider as SizeProviderImpl).dividerSize
        assertNull(providerSize)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - sizeProvider invoked - returns decoration with SizeProvider`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        val expectedProvider = mock<SizeProvider>()

        val decoration = dividerBuilder()
            .sizeProvider(expectedProvider)
            .build()

        val actualProvider = (decoration as DividerItemDecoration).sizeProvider
        assertEquals(expectedProvider, actualProvider)
    }

    @Test
    fun `build - invoked - returns decoration with tint`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .tint(Color.RED)
            .build()

        val provider = (decoration as DividerItemDecoration).tintProvider
        assertTrue(provider is TintProviderImpl)
        @ColorInt val providerTintColor = (provider as TintProviderImpl).dividerTintColor
        assertEquals(Color.RED, providerTintColor)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - tint not invoked - returns decoration with tint from theme`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        @ColorInt val expectedTintColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerTint)

        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).tintProvider
        assertTrue(provider is TintProviderImpl)
        @ColorInt val providerTintColor = (provider as TintProviderImpl).dividerTintColor
        assertEquals(expectedTintColor, providerTintColor)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - tint not invoked, not in theme - returns decoration with null tint`() {
        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).tintProvider
        assertTrue(provider is TintProviderImpl)
        @ColorInt val providerTintColor = (provider as TintProviderImpl).dividerTintColor
        assertNull(providerTintColor)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - tintProvider invoked - returns decoration with TintProvider`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)
        val expectedProvider = mock<TintProvider>()

        val decoration = dividerBuilder()
            .tintProvider(expectedProvider)
            .build()

        val actualProvider = (decoration as DividerItemDecoration).tintProvider
        assertEquals(expectedProvider, actualProvider)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showFirstDivider invoked - returns decoration with first divider visible`() {
        val decoration = dividerBuilder()
            .showFirstDivider()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertTrue((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertFalse(provider.isLastDividerVisible)
        assertFalse(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showLastDivider invoked - returns decoration with last divider visible`() {
        val decoration = dividerBuilder()
            .showLastDivider()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertFalse((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertTrue(provider.isLastDividerVisible)
        assertFalse(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showSideDividers invoked - returns decoration with side dividers visible`() {
        val decoration = dividerBuilder()
            .showSideDividers()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertFalse((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertFalse(provider.isLastDividerVisible)
        assertTrue(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showFirstDivider, showLastDivider invoked - returns decoration with first and last dividers visible`() {
        val decoration = dividerBuilder()
            .showFirstDivider()
            .showLastDivider()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertTrue((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertTrue(provider.isLastDividerVisible)
        assertFalse(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showFirstDivider, showSideDividers invoked - returns decoration with first and side dividers visible`() {
        val decoration = dividerBuilder()
            .showFirstDivider()
            .showSideDividers()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertTrue((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertFalse(provider.isLastDividerVisible)
        assertTrue(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showLastDivider, showSideDividers invoked - returns decoration with last and side dividers visible`() {
        val decoration = dividerBuilder()
            .showLastDivider()
            .showSideDividers()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertFalse((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertTrue(provider.isLastDividerVisible)
        assertTrue(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - showFirstDivider, showLastDivider, showSideDividers invoked - returns decoration with all dividers visible`() {
        val decoration = dividerBuilder()
            .showFirstDivider()
            .showLastDivider()
            .showSideDividers()
            .build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertTrue((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertTrue(provider.isLastDividerVisible)
        assertTrue(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - returns decoration with first, last and side dividers not visible`() {
        val decoration = dividerBuilder().build()

        val provider = (decoration as DividerItemDecoration).visibilityProvider
        assertTrue(provider is VisibilityProviderImpl)
        assertFalse((provider as VisibilityProviderImpl).isFirstDividerVisible)
        assertFalse(provider.isLastDividerVisible)
        assertFalse(provider.areSideDividersVisible)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - visibilityProvider invoked - returns decoration with VisibilityProvider`() {
        val expectedProvider = mock<VisibilityProvider>()

        val decoration = dividerBuilder()
            .visibilityProvider(expectedProvider)
            .build()

        val actualProvider = (decoration as DividerItemDecoration).visibilityProvider
        assertEquals(expectedProvider, actualProvider)
    }

    @Test
    fun `build - offsetProvider invoked - returns decoration with DividerOffsetProvider`() {
        val expectedProvider = mock<DividerOffsetProvider>()

        val decoration = dividerBuilder()
            .offsetProvider(expectedProvider)
            .build()

        val actualProvider = (decoration as DividerItemDecoration).offsetProvider
        assertEquals(expectedProvider, actualProvider)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - drawableProvider invoked, offsetProvider not invoked - logs warning about possible unbalancing`() {
        dividerBuilder()
            .drawableProvider(mock())
            .build()

        verify(logger).logWarning(
            "The default DividerOffsetProvider can't ensure the same size of the items in a grid " +
                "with more than 1 column/row using a custom DrawableProvider, SizeProvider or VisibilityProvider."
        )
    }

    @Test
    fun `build - sizeProvider invoked, offsetProvider not invoked - logs warning about possible unbalancing`() {
        dividerBuilder()
            .sizeProvider(mock())
            .build()

        verify(logger).logWarning(
            "The default DividerOffsetProvider can't ensure the same size of the items in a grid " +
                "with more than 1 column/row using a custom DrawableProvider, SizeProvider or VisibilityProvider."
        )
    }

    @Test
    fun `build - visibilityProvider invoked, offsetProvider not invoked - logs warning about possible unbalancing`() {
        dividerBuilder()
            .visibilityProvider(mock())
            .build()

        verify(logger).logWarning(
            "The default DividerOffsetProvider can't ensure the same size of the items in a grid " +
                "with more than 1 column/row using a custom DrawableProvider, SizeProvider or VisibilityProvider."
        )
    }

    @Test
    fun `build - drawableProvider invoked, offsetProvider invoked - warning is not logged`() {
        dividerBuilder()
            .drawableProvider(mock())
            .offsetProvider(mock())
            .build()

        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - sizeProvider invoked, offsetProvider invoked - warning is not logged`() {
        dividerBuilder()
            .sizeProvider(mock())
            .offsetProvider(mock())
            .build()

        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - visibilityProvider invoked, offsetProvider invoked - warning is not logged`() {
        dividerBuilder()
            .visibilityProvider(mock())
            .offsetProvider(mock())
            .build()

        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - base configuration - returns default decoration`() {
        scenario = launchThemeActivity(R.style.TestTheme_NullAndroidListDivider)

        val decoration = dividerBuilder().build()

        assertFalse((decoration as DividerItemDecoration).asSpace)
        assertEqualDrawables(transparentDrawable(), (decoration.drawableProvider as DrawableProviderImpl).drawable)
        assertEquals(0, (decoration.insetProvider as InsetProviderImpl).dividerInsetStart)
        assertEquals(0, decoration.insetProvider.dividerInsetEnd)
        assertNull((decoration.sizeProvider as SizeProviderImpl).dividerSize)
        assertNull((decoration.tintProvider as TintProviderImpl).dividerTintColor)
        assertFalse((decoration.visibilityProvider as VisibilityProviderImpl).isFirstDividerVisible)
        assertFalse(decoration.visibilityProvider.isLastDividerVisible)
        assertFalse(decoration.visibilityProvider.areSideDividersVisible)
        assertTrue(decoration.cache is InMemoryGridCache)
        verify(logger).logWarning(
            "Can't render the divider without a color/drawable. " +
                "Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" in the theme or set a color/drawable " +
                "in this DividerBuilder."
        )
    }

    @Test
    fun `build - theme configuration - returns decoration with values from theme`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder().build()

        assertTrue((decoration as DividerItemDecoration).asSpace)
        @ColorInt val expectedDrawableColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerDrawable)
        assertEqualDrawables(ColorDrawable(expectedDrawableColor), (decoration.drawableProvider as DrawableProviderImpl).drawable)
        assertEquals(
            resources.pxFromSize(12, TypedValue.COMPLEX_UNIT_DIP),
            (decoration.insetProvider as InsetProviderImpl).dividerInsetStart
        )
        assertEquals(resources.pxFromSize(9, TypedValue.COMPLEX_UNIT_DIP), decoration.insetProvider.dividerInsetEnd)
        assertEquals(resources.pxFromSize(21, TypedValue.COMPLEX_UNIT_DIP), (decoration.sizeProvider as SizeProviderImpl).dividerSize)
        @ColorInt val expectedTintColor = ContextCompat.getColor(context, R.color.test_recyclerViewDividerTint)
        assertEquals(expectedTintColor, (decoration.tintProvider as TintProviderImpl).dividerTintColor)
        assertFalse((decoration.visibilityProvider as VisibilityProviderImpl).isFirstDividerVisible)
        assertFalse(decoration.visibilityProvider.isLastDividerVisible)
        assertFalse(decoration.visibilityProvider.areSideDividersVisible)
        assertTrue(decoration.cache is InMemoryGridCache)
        verifyZeroInteractions(logger)
    }

    @Test
    fun `build - dynamic configuration - returns decoration with values set dynamically`() {
        scenario = launchThemeActivity(R.style.TestTheme_All)

        val decoration = dividerBuilder()
            .asSpace()
            .color(Color.RED)
            .tint(Color.BLACK)
            .size(34)
            .insets(start = 19, end = 67)
            .showFirstDivider()
            .showLastDivider()
            .showSideDividers()
            .build()

        assertTrue((decoration as DividerItemDecoration).asSpace)
        assertEqualDrawables(ColorDrawable(Color.RED), (decoration.drawableProvider as DrawableProviderImpl).drawable)
        assertEquals(19, (decoration.insetProvider as InsetProviderImpl).dividerInsetStart)
        assertEquals(67, decoration.insetProvider.dividerInsetEnd)
        assertEquals(34, (decoration.sizeProvider as SizeProviderImpl).dividerSize)
        assertEquals(Color.BLACK, (decoration.tintProvider as TintProviderImpl).dividerTintColor)
        assertTrue((decoration.visibilityProvider as VisibilityProviderImpl).isFirstDividerVisible)
        assertTrue(decoration.visibilityProvider.isLastDividerVisible)
        assertTrue(decoration.visibilityProvider.areSideDividersVisible)
        assertTrue(decoration.cache is InMemoryGridCache)
        verifyZeroInteractions(logger)
    }

    private fun dividerBuilder(): DividerBuilder = if (::scenario.isInitialized) {
        scenario.letActivity { DividerBuilder(it) }
    } else {
        DividerBuilder(context)
    }
}
