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

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.context
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of CreateStaggeredDivider.kt file.
 */
@RunWith(AndroidJUnit4::class)
class CreateStaggeredDividerKtTest {

    @Test
    fun `staggeredDividerBuilder - new StaggeredDividerBuilder created`() {
        val first = context.staggeredDividerBuilder()
        val second = context.staggeredDividerBuilder()

        assertNotEquals(first, second)
    }

    @Test
    fun `addStaggeredDivider - new StaggeredDividerItemDecoration added to RecyclerView`() {
        val recyclerView = RecyclerView(context)

        assertEquals(0, recyclerView.itemDecorationCount)

        recyclerView.addStaggeredDivider()

        assertEquals(1, recyclerView.itemDecorationCount)
        assertTrue(recyclerView.getItemDecorationAt(0) is StaggeredDividerItemDecoration)
    }
}
