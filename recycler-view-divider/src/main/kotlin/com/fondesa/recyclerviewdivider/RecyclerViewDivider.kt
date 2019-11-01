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
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder
import com.fondesa.recyclerviewdivider.extension.*
import com.fondesa.recyclerviewdivider.manager.drawable.DefaultDrawableManager
import com.fondesa.recyclerviewdivider.manager.drawable.DrawableManager
import com.fondesa.recyclerviewdivider.manager.drawable.asFixed
import com.fondesa.recyclerviewdivider.manager.inset.DefaultInsetManager
import com.fondesa.recyclerviewdivider.manager.inset.InsetManager
import com.fondesa.recyclerviewdivider.manager.inset.asFixed
import com.fondesa.recyclerviewdivider.manager.size.DefaultSizeManager
import com.fondesa.recyclerviewdivider.manager.size.SizeManager
import com.fondesa.recyclerviewdivider.manager.size.asFixed
import com.fondesa.recyclerviewdivider.manager.tint.DefaultTintManager
import com.fondesa.recyclerviewdivider.manager.tint.TintManager
import com.fondesa.recyclerviewdivider.manager.tint.asFixed
import com.fondesa.recyclerviewdivider.manager.visibility.DefaultVisibilityManager
import com.fondesa.recyclerviewdivider.manager.visibility.HideLastVisibilityManager
import com.fondesa.recyclerviewdivider.manager.visibility.VisibilityManager
import com.fondesa.recyclerviewdivider.manager.visibility.asFixed

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
class RecyclerViewDivider internal constructor(
    private val isSpace: Boolean,
    private val drawableManager: DrawableManager,
    private val insetManager: InsetManager,
    private val sizeManager: SizeManager,
    private val tintManager: TintManager?,
    private val visibilityManager: VisibilityManager
) : RecyclerView.ItemDecoration() {

    companion object {
        private val TAG = RecyclerViewDivider::class.java.simpleName

        /**
         * Creates a new [Builder] for the current [Context].
         * The [Builder] instance will be provided by the application if it implements [BuilderProvider].
         * Otherwise, a new base instance will be created.
         *
         * @param context current [Context].
         * @return a new [Builder] instance.
         */
        @JvmStatic
        fun with(context: Context): Builder =
            (context.applicationContext as? BuilderProvider)?.provideDividerBuilder(context)
                ?: Builder(context)
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

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val listSize = parent.adapter?.itemCount ?: 0
        if (listSize <= 0)
            return

        val lm = parent.layoutManager ?: return
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            // Avoid the computation if the position of this view cannot be retrieved.
            return
        }

        val orientation = lm.orientation
        val spanCount = lm.spanCount

        val spanSize: Int
        val lineAccumulatedSpan: Int
        val showDivider: VisibilityManager.VisibilityType
        val divider: Drawable
        @Px var size: Int

        if (lm is StaggeredGridLayoutManager) {
            val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            spanSize = lm.getSpanSize(lp)
            lineAccumulatedSpan = lm.getAccumulatedSpanInLine(lp)

            showDivider = visibilityManager.asFixed().itemVisibility()
            if (showDivider == VisibilityManager.VisibilityType.NONE)
                return

            divider = drawableManager.asFixed().itemDrawable()
            size = sizeManager.asFixed().itemSize(divider, orientation)
        } else {
            val groupCount = lm.getGroupCount(listSize)
            val groupIndex = lm.getGroupIndex(itemPosition)
            spanSize = lm.getSpanSize(itemPosition)
            lineAccumulatedSpan = lm.getAccumulatedSpanInLine(spanSize, itemPosition, groupIndex)
            showDivider = visibilityManager.itemVisibility(groupCount, groupIndex)
            if (showDivider == VisibilityManager.VisibilityType.NONE)
                return

            divider = drawableManager.itemDrawable(groupCount, groupIndex)
            size = sizeManager.itemSize(divider, orientation, groupCount, groupIndex)
        }

        var halfSize = size / 2

        size = if (showDivider == VisibilityManager.VisibilityType.ITEMS_ONLY) 0 else size
        halfSize = if (showDivider == VisibilityManager.VisibilityType.GROUP_ONLY) 0 else halfSize

        val isRTL = parent.isRTL
        val setRect = { leftB: Int, topB: Int, rightB: Int, bottomB: Int ->
            if (isRTL) {
                outRect.set(rightB, topB, leftB, bottomB)
            } else {
                outRect.set(leftB, topB, rightB, bottomB)
            }
        }

        if (orientation == RecyclerView.VERTICAL) {
            when {
                // LinearLayoutManager or GridLayoutManager with 1 column
                spanCount == 1 || spanSize == spanCount -> setRect(0, 0, 0, size)
                // first element in the group
                lineAccumulatedSpan == spanSize -> setRect(0, 0, halfSize, size)
                // last element in the group
                lineAccumulatedSpan == spanCount -> setRect(halfSize, 0, 0, size)
                // element in the middle
                else -> setRect(halfSize, 0, halfSize, size)
            }
        } else {
            when {
                // LinearLayoutManager or GridLayoutManager with 1 row
                spanCount == 1 || spanSize == spanCount -> setRect(0, 0, size, 0)
                // first element in the group
                lineAccumulatedSpan == spanSize -> setRect(0, 0, size, halfSize)
                // last element in the group
                lineAccumulatedSpan == spanCount -> setRect(0, halfSize, size, 0)
                // element in the middle
                else -> setRect(0, halfSize, size, halfSize)
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

        val lm = parent.layoutManager ?: return
        val orientation = lm.orientation
        val spanCount = lm.spanCount
        val childCount = parent.childCount

        val groupCount = if (lm is StaggeredGridLayoutManager) -1 else lm.getGroupCount(listSize)

        val isRTL = parent.isRTL

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val itemPosition = parent.getChildAdapterPosition(child)
            if (itemPosition == RecyclerView.NO_POSITION) {
                // Avoid the computation if the position of at least one view cannot be retrieved.
                return
            }

            val spanSize: Int
            val lineAccumulatedSpan: Int
            val showDivider: VisibilityManager.VisibilityType
            var divider: Drawable
            @Px var size: Int
            @Px var insetBefore: Int
            @Px var insetAfter: Int
            @ColorInt val tint: Int?

            if (lm is StaggeredGridLayoutManager) {
                val lp = child.layoutParams as StaggeredGridLayoutManager.LayoutParams
                spanSize = lm.getSpanSize(lp)
                lineAccumulatedSpan = lm.getAccumulatedSpanInLine(lp)

                showDivider = visibilityManager.asFixed().itemVisibility()
                if (showDivider == VisibilityManager.VisibilityType.NONE) continue

                divider = drawableManager.asFixed().itemDrawable()
                size = sizeManager.asFixed().itemSize(divider, orientation)
                val fixedInsetManager = insetManager.asFixed()
                insetBefore = fixedInsetManager.itemInsetBefore()
                insetAfter = fixedInsetManager.itemInsetAfter()
                tint = tintManager?.asFixed()?.itemTint()
            } else {
                val groupIndex = lm.getGroupIndex(itemPosition)
                spanSize = lm.getSpanSize(itemPosition)
                lineAccumulatedSpan =
                    lm.getAccumulatedSpanInLine(spanSize, itemPosition, groupIndex)
                showDivider = visibilityManager.itemVisibility(groupCount, groupIndex)
                if (showDivider == VisibilityManager.VisibilityType.NONE) continue

                divider = drawableManager.itemDrawable(groupCount, groupIndex)
                size = sizeManager.itemSize(divider, orientation, groupCount, groupIndex)
                insetBefore = insetManager.itemInsetBefore(groupCount, groupIndex)
                insetAfter = insetManager.itemInsetAfter(groupCount, groupIndex)
                tint = tintManager?.itemTint(groupCount, groupIndex)
            }

            if (spanCount > 1 && (insetBefore > 0 || insetAfter > 0)) {
                insetBefore = 0
                insetAfter = 0
                Log.e(TAG, "the inset won't be applied with a span major than 1.")
            }

            tint?.let {
                val wrappedDrawable = DrawableCompat.wrap(divider)
                DrawableCompat.setTint(wrappedDrawable, it)
                divider = wrappedDrawable
            }

            val params = child.layoutParams as RecyclerView.LayoutParams
            val startMargin = params.startMarginCompat
            val topMargin = params.topMargin
            val endMargin = params.endMarginCompat
            val bottomMargin = params.bottomMargin

            var halfSize = if (size < 2) size else size / 2

            size = if (showDivider == VisibilityManager.VisibilityType.ITEMS_ONLY) 0 else size
            halfSize =
                if (showDivider == VisibilityManager.VisibilityType.GROUP_ONLY) 0 else halfSize

            val childBottom = child.bottom
            val childTop = child.top
            val childRight = child.right
            val childLeft = child.left

            // if the last element in the span doesn't complete the span count, its size will be full, not the half
            // halfSize * 2 is used instead of size to handle the case Show.ITEMS_ONLY in which size will be == 0
            val lastElementInSpanSize = if (itemPosition == listSize - 1) halfSize * 2 else halfSize

            val drawWithBounds = { leftB: Int, topB: Int, rightB: Int, bottomB: Int ->
                divider.setBounds(leftB, topB, rightB, bottomB)
                divider.draw(c)
            }

            if (orientation == RecyclerView.VERTICAL) {
                // The RecyclerView is vertical.
                if (spanCount > 1 && spanSize < spanCount) {
                    top = childTop - topMargin
                    // size is added to draw filling point between horizontal and vertical dividers
                    bottom = childBottom + bottomMargin + size

                    val partialDrawAfter = {
                        if (isRTL) {
                            right = childLeft - endMargin
                            left = right - lastElementInSpanSize
                        } else {
                            right = childRight + endMargin
                            left = right + lastElementInSpanSize
                        }
                        drawWithBounds(left, top, right, bottom)
                    }

                    val partialDrawBefore = {
                        if (isRTL) {
                            right = childRight + startMargin
                            left = right + lastElementInSpanSize
                        } else {
                            right = childLeft - startMargin
                            left = right - lastElementInSpanSize
                        }
                        drawWithBounds(left, top, right, bottom)
                    }

                    when (lineAccumulatedSpan) {
                        // First element.
                        spanSize -> partialDrawAfter()
                        // Last element.
                        spanCount -> partialDrawBefore()
                        // Element in the middle.
                        else -> {
                            partialDrawBefore()
                            partialDrawAfter()
                        }
                    }
                }

                // draw bottom divider
                top = childBottom + bottomMargin
                bottom = top + size

                if (isRTL) {
                    left = childLeft + insetAfter - endMargin
                    right = childRight - insetBefore + startMargin
                } else {
                    left = childLeft + insetBefore - startMargin
                    right = childRight - insetAfter + endMargin
                }

                drawWithBounds(left, top, right, bottom)

            } else {
                // The RecyclerView is horizontal.
                if (spanCount > 1 && spanSize < spanCount) {
                    // size is added to draw filling point between horizontal and vertical dividers
                    if (isRTL) {
                        left = childLeft - endMargin - size
                        right = childRight + startMargin
                    } else {
                        left = childLeft - startMargin
                        right = childRight + endMargin + size
                    }


                    when (lineAccumulatedSpan) {
                        spanSize -> {
                            // first element in the group
                            top = childBottom + bottomMargin
                            bottom = top + lastElementInSpanSize

                            drawWithBounds(left, top, right, bottom)
                        }
                        spanCount -> {
                            // last element in the group
                            bottom = childTop - topMargin
                            top = bottom - lastElementInSpanSize

                            drawWithBounds(left, top, right, bottom)
                        }
                        else -> {
                            // element in the middle
                            // top half divider
                            bottom = childTop - topMargin
                            top = bottom - lastElementInSpanSize

                            divider.setBounds(left, top, right, bottom)
                            divider.draw(c)

                            // bottom half divider
                            top = childBottom + bottomMargin
                            bottom = top + lastElementInSpanSize

                            drawWithBounds(left, top, right, bottom)
                        }
                    }
                }

                // draw right divider
                bottom = childBottom - insetAfter + bottomMargin
                top = childTop + insetBefore - topMargin
                if (isRTL) {
                    left = childLeft - endMargin
                    right = left - size
                } else {
                    left = childRight + endMargin
                    right = left + size
                }

                drawWithBounds(left, top, right, bottom)
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
        fun inset(@Px insetBefore: Int, @Px insetAfter: Int) =
            insetManager(DefaultInsetManager(insetBefore, insetAfter))

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
        fun visibilityManager(visibilityManager: VisibilityManager) =
            apply { this.visibilityManager = visibilityManager }

        /**
         * Creates a new [RecyclerViewDivider] with given configurations and initializes all values.
         *
         * @return a new [RecyclerViewDivider] with these [Builder] configurations.
         */
        fun build(): RecyclerViewDivider {
            // Get the builder managers or the default ones.
            val drawableManager = drawableManager ?: DefaultDrawableManager()
            val insetManager = insetManager ?: DefaultInsetManager()
            val sizeManager = sizeManager ?: DefaultSizeManager(context)
            val visibilityManager = visibilityManager ?: DefaultVisibilityManager()

            // Creates the divider.
            return RecyclerViewDivider(
                isSpace,
                drawableManager,
                insetManager,
                sizeManager,
                tintManager,
                visibilityManager
            )
        }
    }

    /**
     * Used to provide a common configuration [RecyclerViewDivider.Builder] when a builder is created
     * through [RecyclerViewDivider.with].
     */
    interface BuilderProvider {

        /**
         * Provides an instance of [RecyclerViewDivider.Builder].
         *
         * @param context the [Context] that can be used to access to resources.
         * @return new instance of [RecyclerViewDivider.Builder] with the new configurations set.
         */
        fun provideDividerBuilder(context: Context): Builder
    }
}