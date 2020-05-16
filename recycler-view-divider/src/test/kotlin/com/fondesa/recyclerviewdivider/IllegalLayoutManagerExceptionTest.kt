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

import androidx.recyclerview.widget.LinearLayoutManager
import com.fondesa.recyclerviewdivider.test.customLayoutManager
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests of [IllegalLayoutManagerException].
 */
class IllegalLayoutManagerExceptionTest {

    @Test
    fun `message - null suggested builder class - mentions the unsupported layout manager`() {
        val e = IllegalLayoutManagerException(customLayoutManager()::class.java)

        assertEquals(
            "The layout manager com.fondesa.recyclerviewdivider.test.DummyCustomLayoutManager isn't supported.",
            e.message
        )
    }

    @Test
    fun `message - not null suggested builder class - suggests to use the right builder`() {
        val e = IllegalLayoutManagerException(LinearLayoutManager::class.java, StaggeredDividerBuilder::class.java)

        assertEquals(
            "Use a StaggeredDividerBuilder to handle dividers in a LinearLayoutManager.",
            e.message
        )
    }
}
