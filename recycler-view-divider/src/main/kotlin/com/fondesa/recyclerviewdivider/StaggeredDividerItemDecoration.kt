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

/**
 * Implementation of [BaseDividerItemDecoration] used in a [StaggeredGridLayoutManager].
 *
 * @param asSpace true if the divider should behave as a space.
 * @param drawable the divider's drawable.
 * @param size the divider's size.
 * @param areSideDividersVisible true if the side dividers should be shown.
 */
internal class StaggeredDividerItemDecoration(
    asSpace: Boolean,
    @VisibleForTesting internal val drawable: Drawable,
    @[VisibleForTesting Px] internal val size: Int,
    @VisibleForTesting internal val areSideDividersVisible: Boolean
) : BaseDividerItemDecoration(asSpace) {

    override fun getItemOffsets(
        layoutManager: RecyclerView.LayoutManager,
        outRect: Rect,
        itemView: View,
        itemCount: Int,
        itemIndex: Int
    ) = layoutManager.withStaggered {
        val layoutDirection = obtainLayoutDirection()
        val layoutRightToLeft = layoutDirection.isRightToLeft
        val layoutBottomToTop = layoutDirection.isBottomToTop
        val adjacentGridSides = sidesAdjacentToItem(itemView, layoutRightToLeft)
        @Px val topSize: Int
        @Px val bottomSize: Int
        @Px val startSize: Int
        @Px val endSize: Int
        if (layoutOrientation.isVertical) {
            topSize = 0
            bottomSize = size
            startSize = when {
                Side.START !in adjacentGridSides -> size / 2
                areSideDividersVisible -> size
                else -> 0
            }
            endSize = when {
                // If the divider has an odd size, to render a middle divider equally sized to the ones
                // adjacent to the grid's sides, 1 pixel is added to the first of two adjacent cells.
                Side.END !in adjacentGridSides && size % 2 == 1 -> size / 2 + 1
                Side.END !in adjacentGridSides -> size / 2
                areSideDividersVisible -> size
                else -> 0
            }
        } else {
            topSize = when {
                Side.TOP !in adjacentGridSides -> size / 2
                areSideDividersVisible -> size
                else -> 0
            }
            bottomSize = when {
                // If the divider has an odd size, to render a middle divider equally sized to the ones
                // adjacent to the grid's sides, 1 pixel is added to the first of two adjacent cells.
                Side.BOTTOM !in adjacentGridSides && size % 2 == 1 -> size / 2 + 1
                Side.BOTTOM !in adjacentGridSides -> size / 2
                areSideDividersVisible -> size
                else -> 0
            }
            startSize = 0
            endSize = size
        }
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
        recyclerView.forEachItem { _, itemView -> drawDividersOfItem(canvas, itemView) }
    }

    private fun StaggeredGridLayoutManager.drawDividersOfItem(canvas: Canvas, itemView: View) {
        val layoutDirection = obtainLayoutDirection()
        val layoutRightToLeft = layoutDirection.isRightToLeft
        val layoutBottomToTop = layoutDirection.isBottomToTop
        val left = itemView.leftWithMargin
        val right = itemView.rightWithMargin
        val top = itemView.topWithMargin
        val bottom = itemView.bottomWithMargin
        val adjacentGridSides = sidesAdjacentToItem(itemView, layoutRightToLeft)
        val shouldDrawTop: Boolean
        val shouldDrawBottom: Boolean
        val shouldDrawStart: Boolean
        val shouldDrawEnd: Boolean
        if (layoutOrientation.isVertical) {
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
