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

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario

/**
 * An [AppCompatActivity] used in tests to test different themes.
 */
internal class ThemeTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        intent.getIntExtra(ARG_THEME, -1)
            .takeUnless { it == -1 }
            ?.let { theme -> setTheme(theme) }
        super.onCreate(savedInstanceState)
    }

    companion object {
        private const val ARG_THEME = "theme"

        /**
         * Creates the [Intent] for a [ThemeTestActivity] with the given theme.
         *
         * @param themeRes the resource of the theme which should be set.
         * @return the [Intent] for a [ThemeTestActivity] with the theme resource in the extras.
         */
        fun intent(@StyleRes themeRes: Int): Intent = Intent(context, ThemeTestActivity::class.java)
            .putExtra(ARG_THEME, themeRes)
    }
}

/**
 * Launches a [ThemeTestActivity] with the given theme.
 *
 * @param themeRes the resource of the theme which should be set.
 * @return the [ActivityScenario] of the [ThemeTestActivity] which will be launched.
 */
internal fun launchThemeActivity(@StyleRes themeRes: Int = -1): ActivityScenario<ThemeTestActivity> =
    ActivityScenario.launch(ThemeTestActivity.intent(themeRes))
