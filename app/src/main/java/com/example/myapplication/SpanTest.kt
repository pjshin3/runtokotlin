package com.example.myapplication

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log

class SpanTest {

    fun getText(s : String){
        val item = SpannableString(s)
        item.setSpan(
            s+"LOVE",
            0,
            5,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        println(item)
    }
}