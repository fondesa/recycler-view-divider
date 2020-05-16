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

package com.fondesa.recyclerviewdivider.drawable

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.fondesa.recyclerviewdivider.R
import com.fondesa.recyclerviewdivider.withAttribute

/**
 * Gets the divider [Drawable] from the theme.
 * The default [Drawable] can be defined with the attribute "recyclerViewDividerDrawable" or the attribute "android:listDivider".
 * If they are both defined, the value will be picked from "recyclerViewDividerDrawable".
 * If both of them aren't defined, a null [Drawable] will be returned.
 *
 * @return the [Drawable] picked from the theme or null if it's not defined in the theme.
 */
internal fun Context.getThemeDrawable(): Drawable? = withAttribute(R.attr.recyclerViewDividerDrawable) {
    getDrawable(0) ?: withAttribute(android.R.attr.listDivider) {
        getDrawable(0)
    }
}

/**
 * Creates a transparent [Drawable] used as the default divider.
 *
 * @return a transparent [ColorDrawable] since it's the most lightweight [Drawable].
 */
internal fun transparentDrawable(): Drawable = ColorDrawable(Color.TRANSPARENT)
