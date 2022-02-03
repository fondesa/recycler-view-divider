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

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.drawable.drawWithBounds
import com.fondesa.recyclerviewdivider.offset.StaggeredDividerOffsetProvider
import kotlin.math.roundToInt

/**
 * Implementation of [BaseDividerItemDecoration] used in a [StaggeredGridLayoutManager].
 *
 * @param asSpace true if the divider should behave as a space.
 * @param drawable the divider's drawable.
 * @param size the divider's size.
 * @param areSideDividersVisible true if the side dividers should be shown.
 * @param offsetProvider balances the offsets of the divider.
 */
internal class StaggeredDividerItemDecoration(
    asSpace: Boolean,
    @get:VisibleForTesting internal val drawable: Drawable,
    @get:VisibleForTesting @Px internal val size: Int,
    @get:VisibleForTesting internal val areSideDividersVisible: Boolean,
    private val offsetProvider: StaggeredDividerOffsetProvider
) : BaseDividerItemDecoration(asSpace) {

    override fun getItemOffsets(
        layoutManager: RecyclerView.LayoutManager,
        outRect: Rect,
        itemView: View,
        itemCount: Int,
        itemIndex: Int
    ) = layoutManager.withStaggered {
        val grid = staggeredGrid()
        val cell = itemView.staggeredCell()
        @Px val topSize = offsetProvider.getOffsetFromSize(grid, cell, Side.TOP, size)
        @Px val bottomSize = offsetProvider.getOffsetFromSize(grid, cell, Side.BOTTOM, size)
        @Px val startSize = offsetProvider.getOffsetFromSize(grid, cell, Side.START, size)
        @Px val endSize = offsetProvider.getOffsetFromSize(grid, cell, Side.END, size)
        val layoutRightToLeft = grid.layoutDirection.isRightToLeft
        val layoutBottomToTop = grid.layoutDirection.isBottomToTop
        outRect.top = if (layoutBottomToTop) bottomSize else topSize
        outRect.bottom = if (layoutBottomToTop) topSize else bottomSize
        outRect.left = if (layoutRightToLeft) endSize else startSize
        outRect.right = if (layoutRightToLeft) startSize else endSize
    }

    override fun onDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager,
        itemCount: Int
    ) = layoutManager.withStaggered {
        val grid = staggeredGrid()
        recyclerView.forEachItem(itemCount) { _, itemView -> itemView.drawDividersOfItem(canvas, grid) }
    }

    private fun View.drawDividersOfItem(canvas: Canvas, grid: StaggeredGrid) {
        val layoutRightToLeft = grid.layoutDirection.isRightToLeft
        val layoutBottomToTop = grid.layoutDirection.isBottomToTop
        val translationX = translationX.roundToInt()
        val translationY = translationY.roundToInt()
        val left = leftWithMargin + translationX
        val right = rightWithMargin + translationX
        val top = topWithMargin + translationY
        val bottom = bottomWithMargin + translationY
        val cell = staggeredCell()
        val adjacentGridSides = grid.sidesAdjacentToCell(cell)
        val shouldDrawTop: Boolean
        val shouldDrawBottom: Boolean
        val shouldDrawStart: Boolean
        val shouldDrawEnd: Boolean
        if (grid.orientation.isVertical) {
            shouldDrawBottom = true
            shouldDrawTop = true
            shouldDrawEnd = Side.END !in adjacentGridSides || areSideDividersVisible
            shouldDrawStart = Side.START !in adjacentGridSides || areSideDividersVisible
        } else {
            shouldDrawBottom = Side.BOTTOM !in adjacentGridSides || areSideDividersVisible
            shouldDrawTop = Side.TOP !in adjacentGridSides || areSideDividersVisible
            shouldDrawEnd = true
            shouldDrawStart = true
        }

        if (shouldDrawEnd) {
            val leftBound = if (layoutRightToLeft) left - size else right
            val rightBound = if (layoutRightToLeft) left else right + size
            drawable.drawWithBounds(canvas = canvas, left = leftBound, top = top - size, right = rightBound, bottom = bottom + size)
        }

        if (shouldDrawStart) {
            val leftBound = if (layoutRightToLeft) right else left - size
            val rightBound = if (layoutRightToLeft) right + size else left
            drawable.drawWithBounds(canvas = canvas, left = leftBound, top = top - size, right = rightBound, bottom = bottom + size)
        }

        if (shouldDrawTop) {
            val topBound = if (layoutBottomToTop) bottom else top - size
            val bottomBound = if (layoutBottomToTop) bottom + size else top
            drawable.drawWithBounds(canvas = canvas, left = left, top = topBound, right = right, bottom = bottomBound)
        }

        if (shouldDrawBottom) {
            val topBound = if (layoutBottomToTop) top - size else bottom
            val bottomBound = if (layoutBottomToTop) top else bottom + size
            drawable.drawWithBounds(canvas = canvas, left = left, top = topBound, right = right, bottom = bottomBound)
        }
    }

    private inline fun RecyclerView.LayoutManager.withStaggered(onStaggered: StaggeredGridLayoutManager.() -> Unit) {
        when (this) {
            is StaggeredGridLayoutManager -> onStaggered()
            is LinearLayoutManager -> throw IllegalLayoutManagerException(
                layoutManagerClass = this::class.java,
                suggestedBuilderClass = DividerBuilder::class.java
            )
            else -> throw IllegalLayoutManagerException(layoutManagerClass = this::class.java)
        }
    }
}
