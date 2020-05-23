package com.example.myapplication

import android.os.Build
import kotlinx.android.synthetic.main.main.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class UnitTest {
    @Test
    fun `textview test`(){
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        assertEquals("test is good", activity.test_tv.text)
    }
}