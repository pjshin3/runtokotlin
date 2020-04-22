package com.example.myapplication

import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test

class CoroutinTest {

    val test = CoroutinClass()

    @InternalCoroutinesApi
    @Test
    fun test(){
        test.main()
    }

}