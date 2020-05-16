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

import android.graphics.Canvas
import android.graphics.drawable.Drawable

/**
 * Draws this [Drawable] on the given [Canvas].
 *
 * @param canvas the [Canvas] on which the [Drawable] should be drawn.
 * @param left the [Drawable]'s left bound.
 * @param left the [Drawable]'s top bound.
 * @param left the [Drawable]'s right bound.
 * @param left the [Drawable]'s bottom bound.
 */
internal fun Drawable.drawWithBounds(canvas: Canvas, left: Int, top: Int, right: Int, bottom: Int) {
    setBounds(left, top, right, bottom)
    draw(canvas)
}
