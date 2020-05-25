package com.example.myapplication

import android.graphics.Color.RED
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val itme = SpannableString("testestestasdfasdfwekrlqwekr")
        itme.setSpan(
            this.toString().get(0)+ 5,
            5,
            10,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        test_tv.text = itme.toString()

    }


}