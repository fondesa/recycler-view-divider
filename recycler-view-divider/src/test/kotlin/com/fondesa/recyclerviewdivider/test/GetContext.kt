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

import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Gets the target [Context].
 */
internal val context: Context get() = InstrumentationRegistry.getInstrumentation().targetContext

/**
 * Gets the [Resources] of the target [Context]
 */
internal val resources: Resources get() = context.resources
