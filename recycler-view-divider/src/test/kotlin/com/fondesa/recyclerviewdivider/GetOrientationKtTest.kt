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

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.linearLayoutManager
import com.fondesa.recyclerviewdivider.test.staggeredLayoutManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of GetOrientation.kt file.
 */
@RunWith(AndroidJUnit4::class)
class GetOrientationKtTest {

    @Test
    fun `layoutOrientation - horizontal LinearLayoutManager - returns Orientation$HORIZONTAL`() {
        val layoutManager = linearLayoutManager(Orientation.HORIZONTAL, false)

        assertEquals(Orientation.HORIZONTAL, layoutManager.layoutOrientation)
    }

    @Test
    fun `layoutOrientation - vertical LinearLayoutManager - returns Orientation$VERTICAL`() {
        val layoutManager = linearLayoutManager(Orientation.VERTICAL, false)

        assertEquals(Orientation.VERTICAL, layoutManager.layoutOrientation)
    }

    @Test
    fun `layoutOrientation - horizontal StaggeredGridLayoutManager - returns Orientation$HORIZONTAL`() {
        val layoutManager = staggeredLayoutManager(3, Orientation.HORIZONTAL)

        assertEquals(Orientation.HORIZONTAL, layoutManager.layoutOrientation)
    }

    @Test
    fun `layoutOrientation - vertical StaggeredGridLayoutManager - returns Orientation$VERTICAL`() {
        val layoutManager = staggeredLayoutManager(3, Orientation.VERTICAL)

        assertEquals(Orientation.VERTICAL, layoutManager.layoutOrientation)
    }
}
