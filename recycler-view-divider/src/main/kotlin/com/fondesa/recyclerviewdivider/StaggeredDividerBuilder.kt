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
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.drawable.getThemeDrawable
import com.fondesa.recyclerviewdivider.drawable.isOpaque
import com.fondesa.recyclerviewdivider.drawable.transparentDrawable
import com.fondesa.recyclerviewdivider.log.logWarning
import com.fondesa.recyclerviewdivider.offset.StaggeredDividerOffsetProvider
import com.fondesa.recyclerviewdivider.size.getDefaultSize
import com.fondesa.recyclerviewdivider.size.getThemeSize
import com.fondesa.recyclerviewdivider.space.getThemeAsSpaceOrDefault

/**
 * Builds a divider for a [StaggeredGridLayoutManager] or its subclasses.
 * The following properties of a divider can be configured:
 *
 * - asSpace
 * Makes the divider as a simple space, so the divider leaves the space between two cells but it is not rendered.
 * Since the rendering is totally skipped in this case, it's better than using a transparent color.
 * The asSpace property can be defined in the theme with the attribute "recyclerViewDividerAsSpace".
 * By default it's false.
 *
 * - color
 * Specifies the color of the divider used when it will be rendered.
 * The color of the divider can be defined in the theme with the attribute "recyclerViewDividerDrawable".
 * If it's not defined, it will be picked by the attribute "android:listDivider".
 * If the Android attribute's value is null, the divider won't be rendered and it will behave as a space.
 * There are two cases in which this builder can't ensure a correct rendering of a divider:
 * - If the value of "recyclerViewDividerDrawable" or "android:listDivider" is not a solid color.
 * - If the color of this divider is not completely opaque (contains some transparency).
 *
 * - size
 * Specifies the divider's size.
 * The size of the divider can be defined in the theme with the attribute "recyclerViewDividerSize".
 * If it's not defined, the divider size will be inherited from the divider's drawable.
 * If it can't be inherited from the divider's drawable (e.g. the drawable is a solid color), the default size of 1dp will be used.
 * The size of an horizontal divider is its height.
 * The size of a vertical divider is its width.
 *
 * - visibility
 * Specifies if a divider is visible or not.
 * When the divider isn't visible, it means the divider won't exist on the layout so its space between the two adjacent cells won't appear.
 * By default all the dividers are visible except the divider before the first items.
 * This happens because, apparently, it's not possible to understand which items will appear first in a [StaggeredGridLayoutManager] since
 * their positions can change.
 *
 * @param context the [Context] used to build the divider.
 */
class StaggeredDividerBuilder(private val context: Context) {
    private var asSpace = false
    @ColorInt private var color: Int? = null
    @Px private var size: Int? = null
    private var areSideDividersVisible = true

    /**
     * Sets the divider as a simple space, so the divider leaves the space between two cells but it is not rendered.
     * Since the rendering is totally skipped in this case, it's better than using a transparent color.
     *
     * @return this [StaggeredDividerBuilder] instance.
     */
    fun asSpace(): StaggeredDividerBuilder = apply { asSpace = true }

    /**
     * Sets the divider's color.
     * If the color of this divider is not completely opaque (contains some transparency), this builder can't ensure a correct rendering.
     *
     * @param colorRes the resource of the color used for each divider.
     * @return this [StaggeredDividerBuilder] instance.
     */
    fun colorRes(@ColorRes colorRes: Int): StaggeredDividerBuilder = color(ContextCompat.getColor(context, colorRes))

    /**
     * Sets the divider's color.
     * If the color of this divider is not completely opaque (contains some transparency), this builder can't ensure a correct rendering.
     *
     * @param color the color used for each divider.
     * @return this [StaggeredDividerBuilder] instance.
     */
    fun color(@ColorInt color: Int): StaggeredDividerBuilder = apply { this.color = color }

    /**
     * Sets the divider's size.
     * The size of an horizontal divider is its height.
     * The size of a vertical divider is its width.
     *
     * @param size the divider's size.
     * @param sizeUnit the divider's size measurement unit (e.g. [TypedValue.COMPLEX_UNIT_DIP]). By default pixels.
     * @return this [StaggeredDividerBuilder] instance.
     */
    @JvmOverloads
    fun size(size: Int, sizeUnit: Int = TypedValue.COMPLEX_UNIT_PX): StaggeredDividerBuilder = apply {
        this.size = context.resources.pxFromSize(size = size, sizeUnit = sizeUnit)
    }

    /**
     * Hides the side dividers of the grid.
     * The side dividers are the ones before the first column and after the last column.
     *
     * @return this [StaggeredDividerBuilder] instance.
     */
    fun hideSideDividers(): StaggeredDividerBuilder = apply { areSideDividersVisible = false }

    /**
     * Creates the [RecyclerView.ItemDecoration] using the configuration specified in this builder.
     * If this builder doesn't specify the configuration of some divider's properties, those properties will be picked from the theme
     * or their default will be used.
     *
     * @return a new [RecyclerView.ItemDecoration] which can be attached to the [RecyclerView].
     */
    fun build(): BaseDividerItemDecoration {
        val asSpace = asSpace || context.getThemeAsSpaceOrDefault()
        return StaggeredDividerItemDecoration(
            asSpace = asSpace,
            drawable = color?.let { ColorDrawable(it) } ?: context.getThemeDrawableOrDefault(asSpace),
            size = size ?: context.getThemeSize() ?: context.getDefaultSize(),
            areSideDividersVisible = areSideDividersVisible,
            offsetProvider = StaggeredDividerOffsetProvider(areSideDividersVisible)
        )
    }

    private fun Context.getThemeDrawableOrDefault(asSpace: Boolean): Drawable {
        val drawable = getThemeDrawable()
        // If the divider should be treated as a space, its drawable isn't necessary so the logs can be skipped.
        if (asSpace) return drawable ?: transparentDrawable()
        when {
            drawable == null -> {
                "Can't render the divider without a color. Specify \"recyclerViewDividerDrawable\" or \"android:listDivider\" " +
                    "in the theme or set a color in this ${StaggeredDividerBuilder::class.java.simpleName}."
            }
            drawable !is ColorDrawable -> {
                "Can't ensure the correct rendering of a divider drawable which isn't a solid color in a " +
                    "${StaggeredGridLayoutManager::class.java.simpleName}."
            }
            !drawable.isOpaque -> {
                "Can't ensure the correct rendering of a divider color which has alpha in a " +
                    "${StaggeredGridLayoutManager::class.java.simpleName}."
            }
            else -> null
        }?.let { warningMsg -> logWarning(warningMsg) }
        return drawable ?: transparentDrawable()
    }
}
