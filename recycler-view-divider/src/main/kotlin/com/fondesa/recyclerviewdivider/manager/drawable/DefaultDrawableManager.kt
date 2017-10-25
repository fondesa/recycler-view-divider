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

package com.fondesa.recyclerviewdivider.manager.drawable

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import com.fondesa.recycler_view_divider.R

/**
 * Created by antoniolig on 23/10/17.
 */
internal class DefaultDrawableManager(private val drawable: Drawable) : DrawableManager {

    constructor(@ColorInt color: Int) : this(ColorDrawable(color))

    constructor(context: Context): this(ContextCompat.getColor(context, R.color.recycler_view_divider_color))

    override fun itemDrawable(groupCount: Int, groupIndex: Int): Drawable = drawable
}