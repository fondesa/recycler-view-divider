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
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.bounds
import com.fondesa.recyclerviewdivider.test.context
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of ViewBoundsWithMargin.kt file.
 */
@RunWith(AndroidJUnit4::class)
class ViewBoundsWithMarginKtTest {
    private val view = View(context).bounds(30, 30, 30, 30)

    @Test
    fun `topWithMargin - view with null layout params - returns view top`() {
        view.top = 21

        assertEquals(21, view.topWithMargin)
    }

    @Test
    fun `topWithMargin - view without MarginLayoutParams - returns view top`() {
        view.top = 21
        view.layoutParams = layoutParams()

        assertEquals(21, view.topWithMargin)
    }

    @Test
    fun `topWithMargin - view with MarginLayoutParams - returns view top minus top margin`() {
        view.top = 42
        view.layoutParams = marginLayoutParams().apply { topMargin = 13 }

        assertEquals(29, view.topWithMargin)
    }

    @Test
    fun `bottomWithMargin - view with null layout params - returns view bottom`() {
        view.bottom = 21

        assertEquals(21, view.bottomWithMargin)
    }

    @Test
    fun `bottomWithMargin - view without MarginLayoutParams - returns view bottom`() {
        view.bottom = 21
        view.layoutParams = layoutParams()

        assertEquals(21, view.bottomWithMargin)
    }

    @Test
    fun `bottomWithMargin - view with MarginLayoutParams - returns view bottom plus bottom margin`() {
        view.bottom = 42
        view.layoutParams = marginLayoutParams().apply { bottomMargin = 13 }

        assertEquals(55, view.bottomWithMargin)
    }

    @Test
    fun `leftWithMargin - view with null layout params - returns view left`() {
        view.left = 21

        assertEquals(21, view.leftWithMargin)
    }

    @Test
    fun `leftWithMargin - view without MarginLayoutParams - returns view left`() {
        view.left = 21
        view.layoutParams = layoutParams()

        assertEquals(21, view.leftWithMargin)
    }

    @Test
    fun `leftWithMargin - view with MarginLayoutParams - returns view left minus left margin`() {
        view.left = 42
        view.layoutParams = marginLayoutParams().apply { leftMargin = 13 }

        assertEquals(29, view.leftWithMargin)
    }

    @Test
    fun `rightWithMargin - view with null layout params - returns view right`() {
        view.right = 21

        assertEquals(21, view.rightWithMargin)
    }

    @Test
    fun `rightWithMargin - view without MarginLayoutParams - returns view right`() {
        view.right = 21
        view.layoutParams = layoutParams()

        assertEquals(21, view.rightWithMargin)
    }

    @Test
    fun `rightWithMargin - view with MarginLayoutParams - returns view right plus right margin`() {
        view.right = 42
        view.layoutParams = marginLayoutParams().apply { rightMargin = 13 }

        assertEquals(55, view.rightWithMargin)
    }

    private fun layoutParams(): ViewGroup.LayoutParams = ViewGroup.LayoutParams(0, 0)

    private fun marginLayoutParams(): ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(0, 0).apply {
        topMargin = 20
        bottomMargin = 20
        rightMargin = 20
        leftMargin = 20
    }
}
