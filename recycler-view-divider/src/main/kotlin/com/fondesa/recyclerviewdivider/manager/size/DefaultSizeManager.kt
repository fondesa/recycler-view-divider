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

package com.fondesa.recyclerviewdivider.manager.size

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.Px
import android.support.v7.widget.RecyclerView
import com.fondesa.recycler_view_divider.R

/**
 * Default implementation of [SizeManager] that will use the same size for each item.
 *
 * @param defaultSize the size that will be set for each item.
 */
class DefaultSizeManager(@Px private val defaultSize: Int) : SizeManager {

    /**
     * Constructor that will obtain the default size from the resources.
     *
     * @param context the [Context] used to access the resources.
     */
    constructor(context: Context) : this(context.resources.getDimensionPixelSize(R.dimen.recycler_view_divider_size))

    @Px
    override fun itemSize(drawable: Drawable, orientation: Int, groupCount: Int, groupIndex: Int): Int {
        val size = if (orientation == RecyclerView.VERTICAL) drawable.intrinsicHeight else drawable.intrinsicWidth
        // If the size is equal to -1, it means that the drawable's sizes can't be defined, e.g. ColorDrawable.
        return if (size == -1) defaultSize else size
    }
}