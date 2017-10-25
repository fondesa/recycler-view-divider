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

package com.fondesa.recyclerviewdivider.manager.inset

import android.content.Context
import android.support.annotation.Px
import com.fondesa.recycler_view_divider.R

/**
 * Created by antoniolig on 23/10/17.
 */
class DefaultInsetManager(@Px private val insetBefore: Int,
                          @Px private val insetAfter: Int) : InsetManager {

    constructor(context: Context): this(context.resources.getDimensionPixelSize(R.dimen.recycler_view_divider_inset_before),
            context.resources.getDimensionPixelSize(R.dimen.recycler_view_divider_inset_after))

    @Px
    override fun itemInsetBefore(groupCount: Int, groupIndex: Int): Int = insetBefore

    @Px
    override fun itemInsetAfter(groupCount: Int, groupIndex: Int): Int = insetAfter
}