/*
 * Copyright (c) 2017 Fondesa
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
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder
import com.fondesa.recyclerviewdivider.extensions.*
import com.fondesa.recyclerviewdivider.factories.*
import com.fondesa.recyclerviewdivider.manager.drawable.DefaultDrawableManager
import com.fondesa.recyclerviewdivider.manager.drawable.DrawableManager
import com.fondesa.recyclerviewdivider.manager.inset.DefaultInsetManager
import com.fondesa.recyclerviewdivider.manager.inset.InsetManager
import com.fondesa.recyclerviewdivider.manager.size.DefaultSizeManager
import com.fondesa.recyclerviewdivider.manager.size.SizeManager
import com.fondesa.recyclerviewdivider.manager.tint.DefaultTintManager
import com.fondesa.recyclerviewdivider.manager.tint.TintManager
import com.fondesa.recyclerviewdivider.manager.visibility.DefaultVisibilityManager
import com.fondesa.recyclerviewdivider.manager.visibility.HideLastVisibilityManager
import com.fondesa.recyclerviewdivider.manager.visibility.VisibilityManager

/**
 * Used to draw a divider between [RecyclerView]'s elements.
 *
 * @param isSpace true if the divider must be managed like a simple space.
 * @param drawableManager instance of [DrawableManager] taken from [Builder].
 * @param insetManager instance of [DrawableManager] taken from [Builder].
 * @param sizeManager instance of [SizeManager] taken from [Builder].
 * @param tintManager instance of [TintManager] taken from [Builder].
 * @param visibilityManager instance of [VisibilityManager] taken from [Builder].
 */
class RecyclerViewDivider(private val isSpace: Boolean,
                          private val drawableManager: DrawableManager,
                          private val insetManager: InsetManager,
                          private val sizeManager: SizeManager,
                          private val tintManager: TintManager?,
                          private val visibilityManager: VisibilityManager) : RecyclerView.ItemDecoration() {

    companion object {
        private val TAG = RecyclerViewDivider::class.java.simpleName

        /**
         * Creates a new [Builder] for the current [Context].
         *
         * @param context current [Context].
         * @return a new [Builder] instance.
         */
        @JvmStatic
        fun with(context: Context): Builder = Builder(context)
    }

    /**
     * Add this divider to a [RecyclerView].
     *
     * @param recyclerView [RecyclerView] at which the divider will be added.
     */
    fun addTo(recyclerView: RecyclerView) {
        removeFrom(recyclerView)
        recyclerView.addItemDecoration(this)
    }

    /**
     * Remove this divider from a [RecyclerView].
     *
     * @param recyclerView [RecyclerView] from which the divider will be removed.
     */
    fun removeFrom(recyclerView: RecyclerView) {
        recyclerView.removeItemDecoration(this)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val listSize = parent.adapter.itemCount
        if (listSize <= 0)
            return

        val lm = parent.layoutManager
        val itemPosition = parent.getChildAdapterPosition(view)
        val groupCount = lm.getGroupCount(listSize)
        val groupIndex = lm.getGroupIndex(itemPosition)

        @VisibilityManager.Show val showDivider: Long = visibilityManager.itemVisibility(groupCount, groupIndex)
        if (showDivider == VisibilityManager.SHOW_NONE)
            return

        val orientation = lm.orientation
        val spanCount = lm.spanCount
        val spanSize = lm.getSpanSize(itemPosition)

        val lineAccumulatedSpan = lm.getAccumulatedSpanInLine(spanSize, itemPosition, groupIndex)

        val divider = drawableManager.itemDrawable(groupCount, groupIndex)
        var size = sizeManager.itemSize(divider, orientation, groupCount, groupIndex)

        val insetBefore: Int
        val insetAfter: Int
        if (spanCount > 1) {
            insetBefore = 0
            insetAfter = 0
            Log.e(TAG, "the inset won't be applied with a span major than 1.")
        } else {
            insetBefore = insetManager.itemInsetBefore(groupCount, groupIndex)
            insetAfter = insetManager.itemInsetAfter(groupCount, groupIndex)
        }

        var halfSize = size / 2

        size = if (showDivider == VisibilityManager.SHOW_ITEMS_ONLY) 0 else size
        halfSize = if (showDivider == VisibilityManager.SHOW_GROUP_ONLY) 0 else halfSize

        if (orientation == RecyclerView.VERTICAL) {
            when {
            // LinearLayoutManager or GridLayoutManager with 1 column
                spanCount == 1 || spanSize == spanCount -> outRect.set(0, 0, 0, size)
            // first element in the group
                lineAccumulatedSpan == spanSize -> outRect.set(0, 0, halfSize + insetAfter, size)
            // last element in the group
                lineAccumulatedSpan == spanCount -> outRect.set(halfSize + insetBefore, 0, 0, size)
            // element in the middle
                else -> outRect.set(halfSize + insetBefore, 0, halfSize + insetAfter, size)
            }
        } else {
            when {
            // LinearLayoutManager or GridLayoutManager with 1 row
                spanCount == 1 || spanSize == spanCount -> outRect.set(0, 0, size, 0)
            // first element in the group
                lineAccumulatedSpan == spanSize -> outRect.set(0, 0, size, halfSize + insetAfter)
            // last element in the group
                lineAccumulatedSpan == spanCount -> outRect.set(0, halfSize + insetBefore, size, 0)
            // element in the middle
                else -> outRect.set(0, halfSize + insetBefore, size, halfSize + insetAfter)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        val listSize = adapter?.itemCount ?: 0

        // if the divider isn't a simple space, it will be drawn
        if (isSpace || listSize == 0)
            return

        var left: Int
        var top: Int
        var right: Int
        var bottom: Int

        val lm = parent.layoutManager
        val orientation = lm.orientation
        val spanCount = lm.spanCount
        val childCount = parent.childCount

        val groupCount = lm.getGroupCount(listSize)

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val itemPosition = parent.getChildAdapterPosition(child)
            val groupIndex = lm.getGroupIndex(itemPosition)

            @VisibilityManager.Show val showDivider: Long = visibilityManager.itemVisibility(groupCount, groupIndex)

            if (showDivider == VisibilityManager.SHOW_NONE) continue

            var divider = drawableManager.itemDrawable(groupCount, groupIndex)
            var size = sizeManager.itemSize(divider, orientation, groupCount, groupIndex)
            val spanSize = lm.getSpanSize(itemPosition)
            val lineAccumulatedSpan = lm.getAccumulatedSpanInLine(spanSize, itemPosition, groupIndex)

            val setBoundsAndDraw = { leftB: Int, topB: Int, rightB: Int, bottomB: Int ->
                divider.setBounds(leftB, topB, rightB, bottomB)
                divider.draw(c)
            }

            val insetBefore: Int
            val insetAfter: Int
            if (spanCount > 1) {
                insetBefore = 0
                insetAfter = 0
                Log.e(TAG, "the inset won't be applied with a span major than 1.")
            } else {
                insetBefore = insetManager.itemInsetBefore(groupCount, groupIndex)
                insetAfter = insetManager.itemInsetAfter(groupCount, groupIndex)
            }

            tintManager?.let {
                val tint = it.itemTint(groupCount, groupIndex)
                val wrappedDrawable = DrawableCompat.wrap(divider)
                DrawableCompat.setTint(wrappedDrawable, tint)
                divider = wrappedDrawable
            }

            val params = child.layoutParams as RecyclerView.LayoutParams

            var halfSize = if (size < 2) size else size / 2

            size = if (showDivider == VisibilityManager.SHOW_ITEMS_ONLY) 0 else size
            halfSize = if (showDivider == VisibilityManager.SHOW_GROUP_ONLY) 0 else halfSize

            val childBottom = child.bottom
            val childTop = child.top
            val childRight = child.right
            val childLeft = child.left

            // if the last element in the span doesn't complete the span count, its size will be full, not the half
            // halfSize * 2 is used instead of size to handle the case Show.ITEMS_ONLY in which size will be == 0
            val lastElementInSpanSize = if (itemPosition == listSize - 1) halfSize * 2 else halfSize

            var marginToAddAfter = 0
            var marginToAddBefore = marginToAddAfter

            if (orientation == RecyclerView.VERTICAL) {
                if (spanCount > 1 && spanSize < spanCount) {
                    top = childTop
                    // size is added to draw filling point between horizontal and vertical dividers
                    bottom = childBottom

                    if (groupIndex > 0) {
                        top -= params.topMargin
                    }
                    if (groupIndex < groupCount - 1 || size > 0) {
                        bottom += params.bottomMargin
                    }
                    bottom += size

                    when (lineAccumulatedSpan) {
                        spanSize -> {
                            // first element in the group
                            left = childRight + params.rightMargin
                            right = left + lastElementInSpanSize

                            setBoundsAndDraw(left, top, right, bottom)

                            marginToAddAfter = params.rightMargin
                        }
                        spanCount -> {
                            // last element in the group
                            right = childLeft - params.leftMargin
                            left = right - halfSize

                            setBoundsAndDraw(left, top, right, bottom)

                            marginToAddBefore = params.leftMargin
                        }
                        else -> {
                            // element in the middle
                            // left half divider
                            right = childLeft - params.leftMargin
                            left = right - halfSize

                            setBoundsAndDraw(left, top, right, bottom)

                            // right half divider
                            left = childRight + params.rightMargin
                            right = left + lastElementInSpanSize

                            setBoundsAndDraw(left, top, right, bottom)

                            marginToAddAfter = params.rightMargin
                            marginToAddBefore = params.leftMargin
                        }
                    }
                }

                // draw bottom divider
                top = childBottom + params.bottomMargin
                bottom = top + size
                left = childLeft + insetBefore - marginToAddBefore
                right = childRight - insetAfter + marginToAddAfter

                setBoundsAndDraw(left, top, right, bottom)

            } else {
                if (spanCount > 1 && spanSize < spanCount) {
                    left = childLeft
                    // size is added to draw filling point between horizontal and vertical dividers
                    right = childRight
                    if (groupIndex > 0) {
                        left -= params.leftMargin
                    }
                    if (groupIndex < groupCount - 1 || size > 0) {
                        right += params.rightMargin
                    }
                    right += size

                    when (lineAccumulatedSpan) {
                        spanSize -> {
                            // first element in the group
                            top = childBottom + params.bottomMargin
                            bottom = top + lastElementInSpanSize

                            setBoundsAndDraw(left, top, right, bottom)

                            marginToAddAfter = params.bottomMargin
                        }
                        spanCount -> {
                            // last element in the group
                            bottom = childTop - params.topMargin
                            top = bottom - halfSize

                            setBoundsAndDraw(left, top, right, bottom)

                            marginToAddBefore = params.topMargin
                        }
                        else -> {
                            // element in the middle
                            // top half divider
                            bottom = childTop - params.topMargin
                            top = bottom - halfSize

                            divider.setBounds(left, top, right, bottom)
                            divider.draw(c)

                            // bottom half divider
                            top = childBottom + params.bottomMargin
                            bottom = top + lastElementInSpanSize

                            setBoundsAndDraw(left, top, right, bottom)

                            marginToAddAfter = params.bottomMargin
                            marginToAddBefore = params.topMargin
                        }
                    }
                }

                // draw right divider
                bottom = childBottom - insetAfter + marginToAddAfter
                top = childTop + insetBefore - marginToAddBefore
                left = childRight + params.rightMargin
                right = left + size

                setBoundsAndDraw(left, top, right, bottom)
            }

        }
    }

    /**
     * Builder class for [RecyclerViewDivider].
     *
     * @param context [Context] used to access the resources.
     */
    class Builder(private val context: Context) {
        private var drawableManager: DrawableManager? = null
        private var insetManager: InsetManager? = null
        private var sizeManager: SizeManager? = null
        private var tintManager: TintManager? = null
        private var visibilityManager: VisibilityManager? = null

        private var isSpace = false

        /**
         * Set this divider as a space.
         * This method is different from setting the transparent color as divider, because
         * it will not draw anything, so, it's the most optimized one.
         */
        fun asSpace() = apply { isSpace = true }

        /**
         * Set the color of all dividers.
         * <br>
         * To set a custom color for each divider use [drawableManager] instead.
         *
         * @param color resolved color for this divider, not a resource.
         * @return this [Builder] instance.
         */
        fun color(@ColorInt color: Int) = drawable(ColorDrawable(color))

        /**
         * Set the drawable of all dividers.
         * <br>
         * To set a custom drawable for each divider use [drawableManager] instead.
         * Warning: if the span count is major than one and the drawable can't be mirrored,
         * the drawable will not be shown correctly.
         *
         * @param drawable drawable custom drawable for this divider.
         * @return this [Builder] instance.
         */
        fun drawable(drawable: Drawable) = drawableManager(DefaultDrawableManager(drawable))

        /**
         * Hide the divider after the last group of items.
         * <br>
         * Warning: when the spanCount is major than 1, only the divider after
         * the last group will be hidden, the items' dividers, instead, will be shown.
         * <br>
         * If you want to specify a more flexible behaviour, use [visibilityManager] instead.
         *
         * @return this [Builder] instance.
         */
        fun hideLastDivider() = visibilityManager(HideLastVisibilityManager())

        /**
         * Set the inset before and after the divider.
         * It will be equal for all dividers.
         * To set a custom inset for each divider, use [insetManager] instead.
         *
         * @param insetBefore the inset that will be applied before.
         * @param insetAfter the inset that will be applied after.
         * @return this [Builder] instance.
         */
        fun inset(@Px insetBefore: Int, @Px insetAfter: Int) = insetManager(DefaultInsetManager(insetBefore, insetAfter))

        /**
         * Set the size of all dividers. The divider's final size will depend on RecyclerView's orientation:
         * Size is referred to the height of an horizontal divider and to the width of a vertical divider.
         * To set a custom size for each divider use [sizeManager] instead.
         *
         * @param size size in pixels for this divider.
         * @return this [Builder] instance.
         */
        fun size(@Px size: Int) = sizeManager(DefaultSizeManager(size))

        /**
         * Set the tint color of all dividers' drawables.
         * If you want to create a plain divider with a single color, [color] is recommended.
         * To set a custom tint color for each divider's drawable use [tintManager] instead.
         *
         * @param color color that will be used as drawable's tint.
         * @return this [Builder] instance.
         */
        fun tint(@ColorInt color: Int) = tintManager(DefaultTintManager(color))

        /**
         * Set the divider's custom [DrawableManager].
         * Warning: if the span count is major than one and the drawable can't be mirrored,
         * the drawable will not be shown correctly.
         *
         * @param drawableManager custom [DrawableManager] to set.
         * @return this [Builder] instance.
         */
        fun drawableManager(drawableManager: DrawableManager) = apply {
            this.drawableManager = drawableManager
            isSpace = false
        }

        /**
         * Set the divider's custom [InsetManager].
         *
         * @param insetManager custom [InsetManager] to set.
         * @return this [Builder] instance.
         */
        fun insetManager(insetManager: InsetManager) = apply { this.insetManager = insetManager }

        /**
         * Set the divider's custom [SizeManager].
         *
         * @param sizeManager custom [SizeManager] to set.
         * @return this [Builder] instance.
         */
        fun sizeManager(sizeManager: SizeManager) = apply { this.sizeManager = sizeManager }

        /**
         * Set the divider's custom [TintManager].
         *
         * @param tintManager custom [TintManager] to set.
         * @return this [Builder] instance.
         */
        fun tintManager(tintManager: TintManager) = apply { this.tintManager = tintManager }

        /**
         * Set the divider's custom [VisibilityManager].
         * <br>
         * If you want to hide only the last divider use [hideLastDivider] instead.
         *
         * @param visibilityManager custom [VisibilityManager] to set.
         * @return this [Builder] instance.
         */
        fun visibilityManager(visibilityManager: VisibilityManager) = apply { this.visibilityManager = visibilityManager }

        /**
         * Set an equal inset for all dividers.
         * This method is now deprecated, use [inset].
         *
         * @param marginSize margins' size in pixels for this divider.
         * @return this [Builder] instance.
         */
        @Deprecated("This method is now deprecated", ReplaceWith("inset(insetBefore, insetAfter)"))
        fun marginSize(@Px marginSize: Int) = inset(marginSize, marginSize)

        /**
         * The usage of factories is now deprecated, use [drawableManager] instead.
         */
        @Deprecated("This method is now deprecated", ReplaceWith("drawableManager(drawableManager)"))
        fun drawableFactory(drawableFactory: DrawableFactory?) = apply { drawableManager = drawableFactory }

        /**
         * The usage of factories is now deprecated, use [insetManager] instead.
         */
        @Deprecated("This method is now deprecated", ReplaceWith("insetManager(insetManager)"))
        fun marginFactory(marginFactory: MarginFactory?) = apply { insetManager = marginFactory }

        /**
         * The usage of factories is now deprecated, use [tintManager] instead.
         */
        @Deprecated("This method is now deprecated", ReplaceWith("tintManager(tintManager)"))
        fun tintFactory(tintFactory: TintFactory?) = apply { tintManager = tintFactory }

        /**
         * The usage of factories is now deprecated, use [sizeManager] instead.
         */
        @Deprecated("This method is now deprecated", ReplaceWith("sizeManager(sizeManager)"))
        fun sizeFactory(sizeFactory: SizeFactory?) = apply { sizeManager = sizeFactory }

        /**
         * The usage of factories is now deprecated, use [visibilityManager] instead.
         */
        @Deprecated("This method is now deprecated", ReplaceWith("visibilityManager(visibilityManager)"))
        fun visibilityFactory(visibilityFactory: VisibilityFactory?) = apply { visibilityManager = visibilityFactory }

        /**
         * Creates a new [RecyclerViewDivider] with given configurations and initializes all values.
         *
         * @return a new [RecyclerViewDivider] with these [Builder] configurations.
         */
        fun build(): RecyclerViewDivider {
            // Get the builder managers or the default ones.
            val drawableManager = drawableManager ?: DefaultDrawableManager(context)
            val insetManager = insetManager ?: DefaultInsetManager(context)
            val sizeManager = sizeManager ?: DefaultSizeManager(context)
            val visibilityManager = visibilityManager ?: DefaultVisibilityManager()

            // Creates the divider.
            return RecyclerViewDivider(isSpace,
                    drawableManager,
                    insetManager,
                    sizeManager,
                    tintManager,
                    visibilityManager)
        }
    }
}