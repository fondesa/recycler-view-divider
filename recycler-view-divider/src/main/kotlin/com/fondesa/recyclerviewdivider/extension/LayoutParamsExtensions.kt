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

package com.fondesa.recyclerviewdivider.extension

import android.view.ViewGroup
import androidx.core.view.MarginLayoutParamsCompat

/**
 * Get the start margin for all APIs.
 *
 * @return the start margin of these [ViewGroup.MarginLayoutParams].
 */
@Deprecated(
    message = "Use the Android API instead.",
    replaceWith = ReplaceWith("MarginLayoutParamsCompat.getMarginStart(this)", "androidx.core.view.MarginLayoutParamsCompat")
)
val ViewGroup.MarginLayoutParams.startMarginCompat: Int
    get() = MarginLayoutParamsCompat.getMarginStart(this)

/**
 * Get the end margin for all APIs.
 *
 * @return the end margin of these [ViewGroup.MarginLayoutParams].
 */
@Deprecated(
    message = "Use the Android API instead.",
    replaceWith = ReplaceWith("MarginLayoutParamsCompat.getMarginEnd(this)", "androidx.core.view.MarginLayoutParamsCompat")
)
val ViewGroup.MarginLayoutParams.endMarginCompat: Int
    get() = MarginLayoutParamsCompat.getMarginEnd(this)
