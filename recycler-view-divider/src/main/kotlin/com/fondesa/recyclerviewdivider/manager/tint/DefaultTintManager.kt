/*
 * Copyright (c) 2020 Fondesa
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

package com.fondesa.recyclerviewdivider.manager.tint

import androidx.annotation.ColorInt
import com.fondesa.recyclerviewdivider.manager.size.SizeManager

/**
 * Default implementation of [SizeManager] that will use the same color to tint each item.
 *
 * @param tint the color used to tint each item.
 */
class DefaultTintManager(@ColorInt private val tint: Int) : FixedTintManager() {

    @ColorInt
    override fun itemTint(): Int = tint
}
