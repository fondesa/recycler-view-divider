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

package com.fondesa.recyclerviewdivider

import android.view.View
import android.view.ViewGroup

/**
 * @return the top of the [View] considering its top margin.
 */
internal val View.topWithMargin: Int get() = top - (marginLayoutParams?.topMargin ?: 0)

/**
 * @return the bottom of the [View] considering its bottom margin.
 */
internal val View.bottomWithMargin: Int get() = bottom + (marginLayoutParams?.bottomMargin ?: 0)

/**
 * @return the left of the [View] considering its left margin.
 */
internal val View.leftWithMargin: Int get() = left - (marginLayoutParams?.leftMargin ?: 0)

/**
 * @return the right of the [View] considering its right margin.
 */
internal val View.rightWithMargin: Int get() = right + (marginLayoutParams?.rightMargin ?: 0)

private val View.marginLayoutParams: ViewGroup.MarginLayoutParams? get() = layoutParams as? ViewGroup.MarginLayoutParams
