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

package com.fondesa.recyclerviewdivider.manager.size

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

/**
 * Default implementation of [SizeManager] that will calculate the size of each item using the
 * drawable's dimensions.
 * If the dimensions can't be calculated, a default size will be used.
 */
class DefaultSizeManager : FixedSizeManager {

    private val defaultSize: Int

    /**
     * Constructor that assigns a default size equal to 1dp.
     *
     * @param context the [Context] used to access the resources.
     */
    constructor(context: Context) {
        val dps = 1
        val scale = context.resources.displayMetrics.density
        this.defaultSize = (dps * scale + 0.5f).toInt()
    }

    /**
     * Constructor that assigns a default size equal to [defaultSize].
     *
     * @param defaultSize the size that will be set for each item.
     */
    constructor(@Px defaultSize: Int) {
        this.defaultSize = defaultSize
    }

    @Px
    override fun itemSize(drawable: Drawable, orientation: Int): Int {
        val size =
            if (orientation == RecyclerView.VERTICAL) drawable.intrinsicHeight else drawable.intrinsicWidth
        // If the size is equal to -1, it means that the drawable's sizes can't be defined, e.g. ColorDrawable.
        return if (size == -1) defaultSize else size
    }
}
