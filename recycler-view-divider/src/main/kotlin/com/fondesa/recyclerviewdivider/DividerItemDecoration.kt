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

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.drawable.DrawableProvider
import com.fondesa.recyclerviewdivider.drawable.drawWithBounds
import com.fondesa.recyclerviewdivider.inset.InsetProvider
import com.fondesa.recyclerviewdivider.size.SizeProvider
import com.fondesa.recyclerviewdivider.tint.TintProvider
import com.fondesa.recyclerviewdivider.visibility.VisibilityProvider

/**
 * Implementation of [DividerItemDecoration] used in a [LinearLayoutManager]/[GridLayoutManager].
 *
 * @param asSpace true if the divider should behave as a space.
 * @param drawableProvider the provider of the divider's drawable.
 * @param insetProvider the provider of the divider's inset.
 * @param sizeProvider the provider of the divider's size.
 * @param sizeProvider the provider of the divider's tint color.
 * @param sizeProvider the provider of the divider's visibility.
 */
internal class DividerItemDecoration(
    asSpace: Boolean,
    @VisibleForTesting internal val drawableProvider: DrawableProvider,
    @VisibleForTesting internal val insetProvider: InsetProvider,
    @VisibleForTesting internal val sizeProvider: SizeProvider,
    @VisibleForTesting internal val tintProvider: TintProvider,
    @VisibleForTesting internal val visibilityProvider: VisibilityProvider
) : BaseDividerItemDecoration(asSpace) {

    override fun getItemOffsets(
        layoutManager: RecyclerView.LayoutManager,
        outRect: Rect,
        itemView: View,
        itemCount: Int,
        itemIndex: Int
    ) = layoutManager.withLinear {
        val grid = grid(itemCount)
        val dividers = grid.dividersAroundCell(absoluteCellIndex = itemIndex)
        val startDivider = dividers.getValue(Side.START)
        val topDivider = dividers.getValue(Side.TOP)
        val bottomDivider = dividers.getValue(Side.BOTTOM)
        val endDivider = dividers.getValue(Side.END)

        val layoutBottomToTop = grid.layoutDirection.isBottomToTop
        val layoutRightToLeft = grid.layoutDirection.isRightToLeft
        topDivider.computeOffsetSize(grid) { dividerSize ->
            val size = if (topDivider.isTopDivider) dividerSize else dividerSize / 2
            if (layoutBottomToTop) outRect.bottom = size else outRect.top = size
        }
        startDivider.computeOffsetSize(grid) { dividerSize ->
            val size = if (startDivider.isStartDivider) dividerSize else dividerSize / 2
            if (layoutRightToLeft) outRect.right = size else outRect.left = size
        }
        bottomDivider.computeOffsetSize(grid) { dividerSize ->
            val size = when {
                bottomDivider.isBottomDivider -> dividerSize
                // If the divider has an odd size, to render a middle divider equally sized to the ones
                // adjacent to the grid's sides, 1 pixel is added to the first of two adjacent cells.
                dividerSize % 2 == 1 -> dividerSize / 2 + 1
                else -> dividerSize / 2
            }
            if (layoutBottomToTop) outRect.top = size else outRect.bottom = size
        }
        endDivider.computeOffsetSize(grid) { dividerSize ->
            val size = when {
                endDivider.isEndDivider -> dividerSize
                // If the divider has an odd size, to render a middle divider equally sized to the ones
                // adjacent to the grid's sides, 1 pixel is added to the first of two adjacent cells.
                dividerSize % 2 == 1 -> dividerSize / 2 + 1
                else -> dividerSize / 2
            }
            if (layoutRightToLeft) outRect.left = size else outRect.right = size
        }
    }

    override fun onDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager,
        itemCount: Int
    ) = layoutManager.withLinear {
        val grid = grid(itemCount)
        recyclerView.forEachItem { itemIndex, itemView -> itemView.drawDividers(canvas, grid, itemIndex) }
    }

    private fun View.drawDividers(canvas: Canvas, grid: Grid, itemIndex: Int) {
        val layoutBottomToTop = grid.layoutDirection.isBottomToTop
        val layoutRightToLeft = grid.layoutDirection.isRightToLeft
        val left = leftWithMargin
        val right = rightWithMargin
        val top = topWithMargin
        val bottom = bottomWithMargin
        val dividers = grid.dividersAroundCell(absoluteCellIndex = itemIndex)
        val startDivider = dividers.getValue(Side.START)
        val topDivider = dividers.getValue(Side.TOP)
        val bottomDivider = dividers.getValue(Side.BOTTOM)
        val endDivider = dividers.getValue(Side.END)

        @Px val topDividerSize = topDivider.takeIf { it.isTopDivider && it.isVisible(grid) }?.draw(grid) { size, insetStart, insetEnd ->
            val insetLeft = if (layoutRightToLeft) insetEnd else insetStart
            val insetRight = if (layoutRightToLeft) insetStart else insetEnd
            val topBound = if (layoutBottomToTop) bottom else top - size
            val bottomBound = if (layoutBottomToTop) bottom + size else top
            drawWithBounds(canvas = canvas, left = left + insetLeft, top = topBound, right = right - insetRight, bottom = bottomBound)
        } ?: 0

        @Px val bottomDividerSize = bottomDivider.takeIf { it.isVisible(grid) }?.draw(grid) { size, insetStart, insetEnd ->
            val insetLeft = if (layoutRightToLeft) insetEnd else insetStart
            val insetRight = if (layoutRightToLeft) insetStart else insetEnd
            val topBound = if (layoutBottomToTop) top - size else bottom
            val bottomBound = if (layoutBottomToTop) top else bottom + size
            drawWithBounds(canvas = canvas, left = left + insetLeft, top = topBound, right = right - insetRight, bottom = bottomBound)
        } ?: 0

        val topFillSize = if (layoutBottomToTop) bottomDividerSize else topDividerSize
        val bottomFillSize = if (layoutBottomToTop) topDividerSize else bottomDividerSize

        startDivider.takeIf { it.isStartDivider && it.isVisible(grid) }?.draw(grid) { size, insetStart, insetEnd ->
            val insetTop = if (layoutBottomToTop) insetEnd else insetStart
            val insetBottom = if (layoutBottomToTop) insetStart else insetEnd
            val filledInsetTop = if (insetTop > 0) insetTop else -topFillSize
            val filledInsetBottom = if (insetBottom > 0) -insetBottom else bottomFillSize
            val leftBound = if (layoutRightToLeft) right else left - size
            val rightBound = if (layoutRightToLeft) right + size else left
            drawWithBounds(
                canvas = canvas,
                left = leftBound,
                top = top + filledInsetTop,
                right = rightBound,
                bottom = bottom + filledInsetBottom
            )
        }

        endDivider.takeIf { it.isVisible(grid) }?.draw(grid) { size, insetStart, insetEnd ->
            val insetTop = if (layoutBottomToTop) insetEnd else insetStart
            val insetBottom = if (layoutBottomToTop) insetStart else insetEnd
            val filledInsetTop = if (insetTop > 0) insetTop else -topFillSize
            val filledInsetBottom = if (insetBottom > 0) -insetBottom else bottomFillSize
            val leftBound = if (layoutRightToLeft) left - size else right
            val rightBound = if (layoutRightToLeft) left else right + size
            drawWithBounds(
                canvas = canvas,
                left = leftBound,
                top = top + filledInsetTop,
                right = rightBound,
                bottom = bottom + filledInsetBottom
            )
        }
    }

    @Px
    private inline fun Divider.draw(grid: Grid, drawBlock: Drawable.(size: Int, insetStart: Int, insetEnd: Int) -> Unit): Int {
        val dividerDrawable = tintedDrawable(grid = grid)
        @Px val dividerSize = sizeProvider.getDividerSize(grid = grid, divider = this, dividerDrawable = dividerDrawable)
        @Px val insetStart = insetProvider.getDividerInsetStart(grid = grid, divider = this)
        @Px val insetEnd = insetProvider.getDividerInsetEnd(grid = grid, divider = this)
        drawBlock(dividerDrawable, dividerSize, insetStart, insetEnd)
        return dividerSize
    }

    private inline fun Divider.computeOffsetSize(grid: Grid, block: (dividerSize: Int) -> Unit) {
        if (!isVisible(grid)) return
        val dividerDrawable = drawableProvider.getDividerDrawable(grid = grid, divider = this)
        @Px val dividerSize = sizeProvider.getDividerSize(grid = grid, divider = this, dividerDrawable = dividerDrawable)
        block(dividerSize)
    }

    private fun Divider.isVisible(grid: Grid): Boolean = visibilityProvider.isDividerVisible(grid = grid, divider = this)

    private fun Divider.tintedDrawable(grid: Grid): Drawable {
        val dividerDrawable = drawableProvider.getDividerDrawable(grid = grid, divider = this)
        val tintColor = tintProvider.getDividerTintColor(grid = grid, divider = this)
        return dividerDrawable.tinted(tintColor)
    }

    private fun Drawable.tinted(@ColorInt tintColor: Int?): Drawable {
        val tintList = tintColor?.let { color -> ColorStateList.valueOf(color) }
        val wrappedDrawable = DrawableCompat.wrap(this)
        DrawableCompat.setTintList(wrappedDrawable, tintList)
        return wrappedDrawable
    }

    private inline fun RecyclerView.LayoutManager.withLinear(onLinear: LinearLayoutManager.() -> Unit) {
        when (this) {
            is LinearLayoutManager -> onLinear()
            is StaggeredGridLayoutManager -> throw IllegalLayoutManagerException(
                layoutManagerClass = this::class.java,
                suggestedBuilderClass = StaggeredDividerBuilder::class.java
            )
            else -> throw IllegalLayoutManagerException(layoutManagerClass = this::class.java)
        }
    }
}