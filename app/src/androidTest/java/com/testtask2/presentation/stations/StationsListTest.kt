package com.testtask2.presentation.stations

import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.testtask2.R
import com.testtask2.presentation.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StationsListTest {

    lateinit var mActivity: MainActivity

    lateinit var mRecyclerView: RecyclerView

    private var itemCount = 0

    @get:Rule
    public val mActivityRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {}

    @get:Rule
    public var permissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    @Before
    fun setUpTest() {
        this.mActivity = this.mActivityRule.activity
        this.mRecyclerView = this.mActivity.findViewById(R.id.rv)

        //grant permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(getInstrumentation().uiAutomation) {
                executeShellCommand("appops set " + InstrumentationRegistry.getTargetContext().packageName + " android:mock_location allow")
                Thread.sleep(1000)
            }
        }

        //set fake location
        val lm = mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        val mocLocationProvider = lm.getBestProvider(criteria, true)

        try {
            lm.addTestProvider(
                mocLocationProvider, false, false,
                false, false, true, true, true, 0, 5
            )
        } catch (e: Exception) {
            Log.e("Log.e", e.message ?: "")
        }

        lm.setTestProviderEnabled(mocLocationProvider, true)
        val loc = Location(mocLocationProvider)
        val mockLocation = Location(mocLocationProvider)
        mockLocation.latitude = 22.813110
        mockLocation.longitude = 113.342434
        mockLocation.altitude = loc.altitude
        mockLocation.time = System.currentTimeMillis()
        mockLocation.accuracy = 1F
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
        }
        lm.setTestProviderLocation(mocLocationProvider, mockLocation)
    }


    @Test
    fun recyclerViewShowItems() {
        Thread.sleep(3000)
        this.itemCount = this.mRecyclerView.adapter?.itemCount!!
        assert(this.itemCount > 0)
    }


    @Test
    fun filterDialogWasShawn(){
        Thread.sleep(3000)
        onView(withId(R.id.filters)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.rgAqi))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }
}