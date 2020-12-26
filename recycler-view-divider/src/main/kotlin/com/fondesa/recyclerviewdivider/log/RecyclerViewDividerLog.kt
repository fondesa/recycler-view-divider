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

import android.util.Log

/**
 * Handles the logging events of the library.
 */
public object RecyclerViewDividerLog {
    internal var logger: Logger? = null
        private set

    init {
        reset()
    }

    /**
     * Defines the [Logger] which should be used to log the events.
     *
     * @param logger the logger used to log the events or null if the events shouldn't be logged.
     */
    @JvmStatic
    public fun use(logger: Logger?) {
        this.logger = logger
    }

    /**
     * Brings the [Logger] to its default value.
     */
    @JvmStatic
    public fun reset() {
        logger = LoggerImpl
    }

    /**
     * Logs the events.
     */
    public interface Logger {

        /**
         * Logs a message treated as a warning.
         *
         * @param msg the message which should be logged.
         */
        public fun logWarning(msg: String)
    }

    private object LoggerImpl : Logger {
        private const val TAG = "RecyclerViewDivider"

        override fun logWarning(msg: String) {
            Log.w(TAG, msg)
        }
    }
}

/**
 * Logs a message treated as a warning.
 *
 * @param msg the message which should be logged.
 */
internal fun logWarning(msg: String) {
    RecyclerViewDividerLog.logger?.logWarning(msg)
}
