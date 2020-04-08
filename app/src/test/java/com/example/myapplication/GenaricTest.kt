package com.example.myapplication

import org.junit.Test

class GenaricTest {
    val test = GenaricTestClass()
    @Test
    fun main(){
        val map = mapOf("hong" to 1)
        test.testMap(map)
        val list = listOf("hong",1)
        test.testEntity(list)
        test.testNumber(123123214141414)
        test.testT("스트링 값으로 줄래 T는 모두 받을수 있어")
        test.testT(131235123123012301)
    }


}