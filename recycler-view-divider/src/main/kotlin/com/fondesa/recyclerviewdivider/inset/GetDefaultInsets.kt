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

package com.fondesa.recyclerviewdivider.inset

import android.content.Context
import androidx.annotation.Px
import com.fondesa.recyclerviewdivider.R
import com.fondesa.recyclerviewdivider.withAttribute

/**
 * Gets the divider's start inset from the theme.
 * The default start inset can be defined with the attribute "recyclerViewDividerInsetStart".
 * If its not defined, 0 will be returned.
 *
 * @return the start inset picked from the theme or 0 if it's not defined in the theme.
 */
@Px
internal fun Context.getThemeInsetStartOrDefault(): Int = withAttribute(R.attr.recyclerViewDividerInsetStart) {
    getDimensionPixelSize(0, 0)
}

/**
 * Gets the divider's end inset from the theme.
 * The default end inset can be defined with the attribute "recyclerViewDividerInsetEnd".
 * If its not defined, 0 will be returned.
 *
 * @return the end inset picked from the theme or 0 if it's not defined in the theme.
 */
@Px
internal fun Context.getThemeInsetEndOrDefault(): Int = withAttribute(R.attr.recyclerViewDividerInsetEnd) {
    getDimensionPixelSize(0, 0)
}
