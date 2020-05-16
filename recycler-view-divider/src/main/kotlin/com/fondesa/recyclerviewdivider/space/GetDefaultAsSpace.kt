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

package com.fondesa.recyclerviewdivider.space

import android.content.Context
import com.fondesa.recyclerviewdivider.R
import com.fondesa.recyclerviewdivider.withAttribute

/**
 * Gets from the theme the value which indicates if the divider should be used as a space or not.
 * The value which indicates it, can be defined with the attribute "recyclerViewDividerAsSpace".
 * If its not defined, false will be returned.
 *
 * @return the value which indicates if the dividers should be used as a space or false if it's not defined in the theme.
 */
internal fun Context.getThemeAsSpaceOrDefault(): Boolean = withAttribute(R.attr.recyclerViewDividerAsSpace) {
    getBoolean(0, false)
}
