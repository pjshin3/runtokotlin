package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText

class CustomClass : androidx.appcompat.widget.AppCompatEditText{
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (text!!.equals("<@sex>")){

        }
    }
}