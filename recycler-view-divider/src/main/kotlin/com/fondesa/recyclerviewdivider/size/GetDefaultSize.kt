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
import android.util.TypedValue
import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.R
import com.fondesa.recyclerviewdivider.pxFromSize
import com.fondesa.recyclerviewdivider.withAttribute

/**
 * Gets the divider's size from the theme.
 * The default divider size can be defined with the attribute "recyclerViewDividerSize".
 * If its not defined, null will be returned.
 *
 * @return the size in pixels picked from the theme or null if it's not defined in the theme.
 */
@Px
internal fun Context.getThemeSize(): Int? = withAttribute(R.attr.recyclerViewDividerSize) {
    getDimensionPixelSize(0, -1).takeIf { it != -1 }
}

/**
 * Gets the default divider size.
 * The default divider size is 1dp.
 *
 * @return the default divider size in pixels.
 */
@Px
internal fun Context.getDefaultSize(): Int = resources.pxFromSize(size = 1, sizeUnit = TypedValue.COMPLEX_UNIT_DIP)
