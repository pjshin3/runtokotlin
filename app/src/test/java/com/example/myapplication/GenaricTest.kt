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
        test.tsetReturn(null, {
            println(it)
        })
        val item = intArrayOf(1,3,4,5,6,7,8,8,8,8,7,6,6)
        test.mayItem(item)
    }


}