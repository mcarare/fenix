/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.helpers

import android.view.ViewConfiguration.getLongPressTimeout
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.mozilla.fenix.HomeActivity
import org.mozilla.fenix.onboarding.FenixOnboarding
import org.mozilla.fenix.ui.robots.appContext

/**
 * A [org.junit.Rule] to handle shared test set up for tests on [HomeActivity].
 *
 * @param initialTouchMode See [ActivityTestRule]
 * @param launchActivity See [ActivityTestRule]
 */

class HomeActivityTestRule(
    initialTouchMode: Boolean = false,
    launchActivity: Boolean = true,
    private val skipOnboarding: Boolean = false
) :
    ActivityTestRule<HomeActivity>(HomeActivity::class.java, initialTouchMode, launchActivity) {
    private val longTapUserPreference = getLongPressTimeout()

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        setLongTapTimeout(3000)
        if (skipOnboarding) { skipOnboardingBeforeLaunch() }
    }

    override fun afterActivityFinished() {
        super.afterActivityFinished()
        setLongTapTimeout(longTapUserPreference)
    }
}

/**
 * A [org.junit.Rule] to handle shared test set up for tests on [HomeActivity]. This adds
 * functionality for using the Espresso-intents api, and extends from ActivityTestRule.
 *
 * @param initialTouchMode See [IntentsTestRule]
 * @param launchActivity See [IntentsTestRule]
 */

class HomeActivityIntentTestRule(
    initialTouchMode: Boolean = false,
    launchActivity: Boolean = true,
    private val skipOnboarding: Boolean = false
) :
    IntentsTestRule<HomeActivity>(HomeActivity::class.java, initialTouchMode, launchActivity) {
    private val longTapUserPreference = getLongPressTimeout()

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        setLongTapTimeout(3000)
        if (skipOnboarding) { skipOnboardingBeforeLaunch() }
    }

    override fun afterActivityFinished() {
        super.afterActivityFinished()
        setLongTapTimeout(longTapUserPreference)
    }
}

// changing the device preference for Touch and Hold delay, to avoid long-clicks instead of a single-click
fun setLongTapTimeout(delay: Int) {
    val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    mDevice.executeShellCommand("settings put secure long_press_timeout $delay")
}

private fun skipOnboardingBeforeLaunch() {
    // The production code isn't aware that we're using
    // this API so it can be fragile.
    FenixOnboarding(appContext).finish()
}
