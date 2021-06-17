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

import android.content.Context
import android.content.res.TypedArray
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * Tests of WithAttribute.kt file.
 */
class WithAttributeKtTest {

    @Test
    fun `withAttribute - returns resolved attribute and recycles TypedArray`() {
        val typedArray = mock<TypedArray>()
        val context = mock<Context> {
            on(it.obtainStyledAttributes(intArrayOf(5))) doReturn typedArray
        }

        val result = context.withAttribute(5) { "resolved" }

        verify(typedArray).recycle()
        assertEquals("resolved", result)
    }
}
