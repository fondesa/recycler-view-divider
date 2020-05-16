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

package com.fondesa.recyclerviewdivider.test

import android.view.View
import android.view.ViewGroup

/**
 * Sets the bounds of the [View].
 *
 * @param left the left bound.
 * @param top the top bound.
 * @param right the right bound.
 * @param bottom the bottom bound.
 * @return this [View].
 */
internal fun View.bounds(left: Int, top: Int, right: Int, bottom: Int): View = apply {
    this.left = left
    this.top = top
    this.right = right
    this.bottom = bottom
}

/**
 * Sets the margins of the [View]
 *
 * @param left the left margin.
 * @param top the top margin.
 * @param right the right margin.
 * @param bottom the bottom margin.
 * @return this [View].
 */
internal fun View.margins(left: Int, top: Int, right: Int, bottom: Int): View = apply {
    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(left, top, right, bottom)
}
