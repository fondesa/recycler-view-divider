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

package com.fondesa.recyclerviewdivider.log

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

/**
 * Tests of RecyclerViewDividerLog.kt file.
 */
@RunWith(AndroidJUnit4::class)
class RecyclerViewDividerLogKtTest {

    @After
    fun bringBackToDefault() {
        RecyclerViewDividerLog.reset()
    }

    @Test
    fun `init - logger is not null`() {
        assertNotNull(RecyclerViewDividerLog.logger)
    }

    @Test
    fun `use - not null Logger as param - logger instance is set`() {
        val newLogger = mock<RecyclerViewDividerLog.Logger>()

        RecyclerViewDividerLog.use(newLogger)

        assertNotNull(RecyclerViewDividerLog.logger)
        assertEquals(newLogger, RecyclerViewDividerLog.logger)
    }

    @Test
    fun `use - null Logger as param - logger instance is removed`() {
        RecyclerViewDividerLog.use(mock())

        RecyclerViewDividerLog.use(null)

        assertNull(RecyclerViewDividerLog.logger)
    }

    @Test
    fun `reset - logger instance returns to its default value`() {
        val default = RecyclerViewDividerLog.logger
        RecyclerViewDividerLog.use(mock())

        RecyclerViewDividerLog.reset()

        assertEquals(default, RecyclerViewDividerLog.logger)
    }

    @Test
    fun `logWarning - not null Logger - message is logged`() {
        val logger = mock<RecyclerViewDividerLog.Logger>()
        RecyclerViewDividerLog.use(logger)

        logWarning("Dummy message")

        verify(logger).logWarning("Dummy message")
    }

    @Test
    fun `logWarning - null Logger - message is not logged`() {
        val logger = mock<RecyclerViewDividerLog.Logger>()
        RecyclerViewDividerLog.use(logger)
        RecyclerViewDividerLog.use(null)

        logWarning("Dummy message")

        verifyNoInteractions(logger)
    }

    @Test
    fun `logWarning - default logger - does not crash`() {
        // It shouldn't crash.
        logWarning("Dummy message")
    }
}
