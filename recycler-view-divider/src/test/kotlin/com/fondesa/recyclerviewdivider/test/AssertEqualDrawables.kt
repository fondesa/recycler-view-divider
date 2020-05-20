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

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import org.junit.Assert.assertTrue

/**
 * Asserts that the [actual] [Drawable] is equal to the [expected] [Drawable].
 * Unfortunately, using Robolectric we can't use [Bitmap] to compare two [Drawable], since the Robolectric implementation
 * translates all the image pixels into transparent ones.
 *
 * @param expected the expected [Drawable].
 * @param actual the actual [Drawable].
 */
internal fun assertEqualDrawables(expected: Drawable?, actual: Drawable?) {
    assertTrue(actual.isEqualTo(expected))
}

private fun Drawable?.isEqualTo(other: Drawable?): Boolean {
    if (this == null && other == null) return true
    return if (this != null && other != null) {
        if (this::class.java != other::class.java) {
            // If the classes are different, they aren't the same Drawable.
            return false
        }
        if (constantState == other.constantState) {
            // If the constant state is identical, the drawables are equal.
            // However, the opposite is not necessarily true.
            return true
        }
        when (this) {
            is ColorDrawable -> isEqualTo(other as ColorDrawable)
            is GradientDrawable -> isEqualTo(other as GradientDrawable)
            is BitmapDrawable -> isEqualTo(other as BitmapDrawable)
            else -> throw IllegalArgumentException("The drawable class ${this::class.java.name} isn't supported.")
        }
    } else {
        false
    }
}

private fun ColorDrawable.isEqualTo(other: ColorDrawable): Boolean = color == other.color

private fun GradientDrawable.isEqualTo(other: GradientDrawable): Boolean {
    val thisDefaultColor = color?.defaultColor
    val otherDefaultColor = other.color?.defaultColor
    if (thisDefaultColor != null && otherDefaultColor != null) return thisDefaultColor == otherDefaultColor
    if (thisDefaultColor == null && otherDefaultColor == null) {
        val thisColors = colors
        val otherColors = other.colors
        if (thisColors != null && otherColors != null) return thisColors.contentEquals(otherColors)
        return thisColors == null && otherColors == null
    }
    return false
}

private fun BitmapDrawable.isEqualTo(other: BitmapDrawable): Boolean = bitmap == other.bitmap
