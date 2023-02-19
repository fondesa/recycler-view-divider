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

package com.fondesa.recyclerviewdivider.size

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Implementation of [SizeProvider] which provides the same size for each divider or infers it from the divider's [Drawable].
 * If the [dividerSize] is specified, each divider will use it.
 * If it's not specified, the divider tries to infer its size from its [Drawable].
 * If it fails to infer its size, the default size will be used ([getDefaultSize]).
 *
 * @param context the [Context] used to retrieve the default divider size.
 * @param dividerSize the divider size of each divider or null if there isn't a defined size.
 */
internal class SizeProviderImpl(
    context: Context,
    /* ktlint-disable annotation */
    @[VisibleForTesting Px] internal val dividerSize: Int?
) : SizeProvider {
    @Px
    private val defaultDividerSize: Int = context.getDefaultSize()

    @Px
    override fun getDividerSize(grid: Grid, divider: Divider, dividerDrawable: Drawable): Int {
        // Uses the specified size if defined.
        dividerSize?.let { return it }
        // Otherwise the size is inherited from the Drawable.
        @Px val size = if (divider.orientation.isHorizontal) dividerDrawable.intrinsicHeight else dividerDrawable.intrinsicWidth
        // If the size is equal to -1, it means the Drawable hasn't a specified size (e.g. it's a ColorDrawable),
        // so the default size is returned.
        return if (size == -1) defaultDividerSize else size
    }
}
