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

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.drawable.DrawableProvider
import com.fondesa.recyclerviewdivider.drawable.DrawableProviderImpl
import com.fondesa.recyclerviewdivider.drawable.getThemeDrawable
import com.fondesa.recyclerviewdivider.drawable.transparentDrawable
import com.fondesa.recyclerviewdivider.inset.InsetProvider
import com.fondesa.recyclerviewdivider.inset.InsetProviderImpl
import com.fondesa.recyclerviewdivider.inset.getThemeInsetEndOrDefault
import com.fondesa.recyclerviewdivider.inset.getThemeInsetStartOrDefault
import com.fondesa.recyclerviewdivider.log.logWarning
import com.fondesa.recyclerviewdivider.offset.DividerOffsetProvider
import com.fondesa.recyclerviewdivider.offset.DividerOffsetProviderImpl
import com.fondesa.recyclerviewdivider.size.SizeProvider
import com.fondesa.recyclerviewdivider.size.SizeProviderImpl
import com.fondesa.recyclerviewdivider.size.getThemeSize
import com.fondesa.recyclerviewdivider.space.getThemeAsSpaceOrDefault
import com.fondesa.recyclerviewdivider.tint.TintProvider
import com.fondesa.recyclerviewdivider.tint.TintProviderImpl
import com.fondesa.recyclerviewdivider.tint.getThemeTintColor
import com.fondesa.recyclerviewdivider.visibility.VisibilityProvider
import com.fondesa.recyclerviewdivider.visibility.VisibilityProviderImpl

/**
 * Builds a divider for a [LinearLayoutManager] or its subclasses (e.g. [GridLayoutManager]).
 * The following properties of a divider can be configured:
 *
 * - asSpace
 * Makes the divider as a simple space, so the divider leaves the space between two cells but it is not rendered.
 * Since the rendering is totally skipped in this case, it's better than using a transparent color.
 * The asSpace property can be defined in the theme with the attribute "recyclerViewDividerAsSpace".
 * By default it's false.
 *
 * - color/drawable
 * Specifies the color/drawable of the divider used when it will be rendered.
 * The color/drawable of the divider can be defined in the theme with the attribute "recyclerViewDividerDrawable".
 * If it's not defined, it will be picked by the attribute "android:listDivider".
 * If the Android attribute's value is null, the divider won't be rendered and it will behave as a space.
 *
 * - insets
 * Specifies the start and the end insets of a divider's color/drawable.
 * The insets of the divider can be defined in the theme with the attributes "recyclerViewDividerInsetStart" and
 * "recyclerViewDividerInsetEnd".
 * The inset will behave as a margin/padding of the divider's drawable.
 * The start inset in an horizontal divider is the left inset in left-to-right and the right inset in right-to-left.
 * The start inset in a vertical divider is the top inset in top-to-bottom and the bottom inset in bottom-to-top.
 * The end inset in an horizontal divider is the right inset in left-to-right and the left inset in right-to-left.
 * The end inset in a vertical divider is the bottom inset in top-to-bottom and the top inset in bottom-to-top.
 * By default the divider has no insets.
 *
 * - size
 * Specifies the divider's size.
 * The size of the divider can be defined in the theme with the attribute "recyclerViewDividerSize".
 * If it's not defined, the divider size will be inherited from the divider's drawable.
 * If it can't be inherited from the divider's drawable (e.g. the drawable is a solid color), the default size of 1dp will be used.
 * The size of an horizontal divider is its height.
 * The size of a vertical divider is its width.
 *
 * - tint
 * Specifies the tint color of the divider's drawable.
 * The tint color of the divider can be defined in the theme with the attribute "recyclerViewDividerTint".
 * By default the divider's drawable isn't tinted.
 *
 * - visibility
 * Specifies if a divider is visible or not.
 * When the divider isn't visible, it means the divider won't exist on the layout so its space between the two adjacent cells won't appear.
 * By default the divider before the first item, after the last item and the side dividers aren't visible, all the others are.
 *
 * - offsets
 * Specifies the divider's offset.
 * It's useful to balance the size of the items in a grid with multiple columns/rows.
 * By default, all the offsets are balanced between the items to render an equal size for each cell.
 *
 * @param context the [Context] used to build the divider.
 */
public class DividerBuilder internal constructor(private val context: Context) {
    private var asSpace = false
    private var drawableProvider: DrawableProvider? = null
    @Px private var insetStart: Int? = null
    @Px private var insetEnd: Int? = null
    private var insetProvider: InsetProvider? = null
    private var sizeProvider: SizeProvider? = null
    private var tintProvider: TintProvider? = null
    private var visibilityProvider: VisibilityProvider? = null
    private var isFirstDividerVisible = false
    private var isLastDividerVisible = false
    private var areSideDividersVisible = false
    private var offsetProvider: DividerOffsetProvider? = null
    private var couldUnbalanceItems = false

    /**
     * Sets the divider as a simple space, so the divider leaves the space between two cells but it is not rendered.
     * Since the rendering is totally skipped in this case, it's better than using a transparent color.
     *
     * @return this [DividerBuilder] instance.
     */
    public fun asSpace(): DividerBuilder = apply { asSpace = true }

    /**
     * Sets the divider's drawable as a solid color.
     *
     * @param colorRes the resource of the color used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun colorRes(@ColorRes colorRes: Int): DividerBuilder = color(ContextCompat.getColor(context, colorRes))

    /**
     * Sets the divider's drawable as a solid color.
     *
     * @param color the color used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun color(@ColorInt color: Int): DividerBuilder = drawable(drawable = ColorDrawable(color))

    /**
     * Sets the divider's drawable.
     *
     * @param drawableRes the resource of the [Drawable] used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun drawableRes(@DrawableRes drawableRes: Int): DividerBuilder = drawable(context.getDrawableCompat(drawableRes))

    /**
     * Sets the divider's drawable.
     *
     * @param drawable the [Drawable] used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun drawable(drawable: Drawable): DividerBuilder =
        drawableProvider(provider = DrawableProviderImpl(drawable = drawable), couldUnbalanceItems = false)

    /**
     * Sets the divider's start inset.
     * The inset will behave as a margin/padding of the divider's drawable.
     * The start inset in an horizontal divider is the left inset in left-to-right and the right inset in right-to-left.
     * The start inset in a vertical divider is the top inset in top-to-bottom and the bottom inset in bottom-to-top.
     *
     * @param start the start inset size in pixels used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun insetStart(@Px start: Int): DividerBuilder = apply { insetStart = start }

    /**
     * Sets the divider's end inset.
     * The inset will behave as a margin/padding of the divider's drawable.
     * The end inset in an horizontal divider is the right inset in left-to-right and the left inset in right-to-left.
     * The end inset in a vertical divider is the bottom inset in top-to-bottom and the top inset in bottom-to-top.
     *
     * @param end the end inset size in pixels used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun insetEnd(@Px end: Int): DividerBuilder = apply { insetEnd = end }

    /**
     * Sets the divider's insets.
     * The inset will behave as a margin/padding of the divider's drawable.
     * The start inset in an horizontal divider is the left inset in left-to-right and the right inset in right-to-left.
     * The start inset in a vertical divider is the top inset in top-to-bottom and the bottom inset in bottom-to-top.
     * The end inset in an horizontal divider is the right inset in left-to-right and the left inset in right-to-left.
     * The end inset in a vertical divider is the bottom inset in top-to-bottom and the top inset in bottom-to-top.
     *
     * @param start the start inset size in pixels used for each divider.
     * @param end the end inset size in pixels used for each divider.
     * @return this [DividerBuilder] instance.
     */
    public fun insets(@Px start: Int, @Px end: Int): DividerBuilder = apply {
        insetStart = start
        insetEnd = end
    }

    /**
     * Sets the divider's size.
     * The size of an horizontal divider is its height.
     * The size of a vertical divider is its width.
     *
     * @param size the divider's size.
     * @param sizeUnit the divider's size measurement unit (e.g. [TypedValue.COMPLEX_UNIT_DIP]). By default pixels.
     * @return this [DividerBuilder] instance.
     */
    @JvmOverloads
    public fun size(size: Int, sizeUnit: Int = TypedValue.COMPLEX_UNIT_PX): DividerBuilder {
        @Px val pxSize = context.resources.pxFromSize(size = size, sizeUnit = sizeUnit)
        return sizeProvider(SizeProviderImpl(context = context, dividerSize = pxSize), couldUnbalanceItems = false)
    }

    /**
     * Sets the tint color of the divider's drawable.
     *
     * @param color the tint color of the divider's drawable.
     * @return this [DividerBuilder] instance.
     */
    public fun tint(@ColorInt color: Int): DividerBuilder = tintProvider(TintProviderImpl(dividerTintColor = color))

    /**
     * Shows the first divider of the list/grid.
     * The first divider is the one before the items in the first line.
     *
     * @return this [DividerBuilder] instance.
     */
    public fun showFirstDivider(): DividerBuilder = apply { isFirstDividerVisible = true }

    /**
     * Shows the last divider of the list/grid.
     * The last divider is the one after the items in the last line.
     *
     * @return this [DividerBuilder] instance.
     */
    public fun showLastDivider(): DividerBuilder = apply { isLastDividerVisible = true }

    /**
     * Shows the side dividers of the list/grid.
     * The side dividers are the ones before the first column and after the last column.
     *
     * @return this [DividerBuilder] instance.
     */
    public fun showSideDividers(): DividerBuilder = apply { areSideDividersVisible = true }

    /**
     * Customizes the drawable of each divider in the grid.
     * IMPORTANT: The default [DividerOffsetProvider] can't ensure the same size of the items in a grid when this method is called.
     * If you want a different behavior, you can specify a custom [DividerOffsetProvider] with [offsetProvider].
     *
     * @param provider the [DrawableProvider] which will be invoked for each divider in the grid.
     * @return this [DividerBuilder] instance.
     */
    public fun drawableProvider(provider: DrawableProvider): DividerBuilder = drawableProvider(provider, couldUnbalanceItems = true)

    /**
     * Customizes the insets of each divider in the grid.
     *
     * @param provider the [InsetProvider] which will be invoked for each divider in the grid.
     * @return this [DividerBuilder] instance.
     */
    public fun insetProvider(provider: InsetProvider): DividerBuilder = apply { insetProvider = provider }

    /**
     * Customizes the size of each divider in the grid.
     * IMPORTANT: The default [DividerOffsetProvider] can't ensure the same size of the items in a grid when this method is called.
     * If you want a different behavior, you can specify a custom [DividerOffsetProvider] with [offsetProvider].
     *
     * @param provider the [SizeProvider] which will be invoked for each divider in the grid.
     * @return this [DividerBuilder] instance.
     */
    public fun sizeProvider(provider: SizeProvider): DividerBuilder = sizeProvider(provider, couldUnbalanceItems = true)

    /**
     * Customizes the tint color of each divider's drawable in the grid.
     *
     * @param provider the [TintProvider] which will be invoked for each divider in the grid.
     * @return this [DividerBuilder] instance.
     */
    public fun tintProvider(provider: TintProvider): DividerBuilder = apply { tintProvider = provider }

    /**
     * Customizes the visibility of each divider in the grid.
     * IMPORTANT: The default [DividerOffsetProvider] can't ensure the same size of the items in a grid when this method is called.
     * If you want a different behavior, you can specify a custom [DividerOffsetProvider] with [offsetProvider].
     *
     * @param provider the [VisibilityProvider] which will be invoked for each divider in the grid.
     * @return this [DividerBuilder] instance.
     */
    public fun visibilityProvider(provider: VisibilityProvider): DividerBuilder = apply {
        visibilityProvider = provider
        couldUnbalanceItems = true
    }

    /**
     * Customizes the offset of each divider in the grid.
     * This method is useful to balance the size of the items in a grid with multiple columns/rows.
     *
     * @param provider the [DividerOffsetProvider] which will be invoked for each divider in the grid.
     * @return this [DividerBuilder] instance.
     */
    public fun offsetProvider(provider: DividerOffsetProvider): DividerBuilder = apply {
        offsetProvider = provider
    }

    /**
     * Creates the [RecyclerView.ItemDecoration] using the configuration specified in this builder.
     * If this builder doesn't specify the configuration of some divider's properties, those properties will be picked from the theme
     * or their default will be used.
     *
     * @return a new [RecyclerView.ItemDecoration] which can be attached to the [RecyclerView].
     */
    public fun build(): BaseDividerItemDecoration {
        val asSpace = asSpace || context.getThemeAsSpaceOrDefault()
        if (offsetProvider == null && couldUnbalanceItems) {
            logWarning(
                "The default ${DividerOffsetProvider::class.java.simpleName} can't ensure the same size of the items in a grid " +
                    "with more than 1 column/row using a custom ${DrawableProvider::class.java.simpleName}, " +
                    "${SizeProvider::class.java.simpleName} or ${VisibilityProvider::class.java.simpleName}."
            )
        }
        return DividerItemDecoration(
            asSpace = asSpace,
            drawableProvider = drawableProvider ?: DrawableProviderImpl(drawable = context.getThemeDrawableOrDefault(asSpace)),
            insetProvider = insetProvider ?: InsetProviderImpl(
                dividerInsetStart = insetStart ?: context.getThemeInsetStartOrDefault(),
                dividerInsetEnd = insetEnd ?: context.getThemeInsetEndOrDefault()
            ),
            sizeProvider = sizeProvider ?: SizeProviderImpl(context = context, dividerSize = context.getThemeSize()),
            tintProvider = tintProvider ?: TintProviderImpl(dividerTintColor = context.getThemeTintColor()),
            visibilityProvider = visibilityProvider ?: VisibilityProviderImpl(
                isFirstDividerVisible = isFirstDividerVisible,
                isLastDividerVisible = isLastDividerVisible,
                areSideDividersVisible = areSideDividersVisible
            ),
            offsetProvider = offsetProvider ?: DividerOffsetProviderImpl(areSideDividersVisible)
        )
    }

    private fun drawableProvider(provider: DrawableProvider, couldUnbalanceItems: Boolean): DividerBuilder = apply {
        drawableProvider = provider
        if (couldUnbalanceItems) {
            this.couldUnbalanceItems = true
        }
    }

    private fun sizeProvider(provider: SizeProvider, couldUnbalanceItems: Boolean): DividerBuilder = apply {
        sizeProvider = provider
        if (couldUnbalanceItems) {
            this.couldUnbalanceItems = true
        }
    }

    private fun Context.getThemeDrawableOrDefault(asSpace: Boolean): Drawable {
        val drawable = getThemeDrawable()
        if (drawable != null) return drawable
        if (!asSpace) {
            // If the divider should be treated as a space, its drawable isn't necessary so the log can be skipped.
            logWarning(
                "Can't render the divider without a color/drawable. " +
                    "Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" in the theme or set a color/drawable " +
                    "in this ${DividerBuilder::class.java.simpleName}."
            )
        }
        return transparentDrawable()
    }

    private fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable = ContextCompat.getDrawable(this, drawableRes)
        ?: throw NullPointerException("The drawable with resource id $drawableRes can't be loaded. Use the method .drawable() instead.")
}
