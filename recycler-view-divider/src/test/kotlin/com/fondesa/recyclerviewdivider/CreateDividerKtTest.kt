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
 * Tests of CreateDivider.kt file.
 */
@RunWith(AndroidJUnit4::class)
class CreateDividerKtTest {

    @Test
    fun `dividerBuilder - new DividerBuilder created`() {
        val first = context.dividerBuilder()
        val second = context.dividerBuilder()

        assertNotEquals(first, second)
    }

    @Test
    fun `addDivider - new DividerItemDecoration added to RecyclerView`() {
        val recyclerView = RecyclerView(context)

        assertEquals(0, recyclerView.itemDecorationCount)

        recyclerView.addDivider()

        assertEquals(1, recyclerView.itemDecorationCount)
        assertTrue(recyclerView.getItemDecorationAt(0) is DividerItemDecoration)
    }
}
