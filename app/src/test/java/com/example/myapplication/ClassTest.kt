package com.example.myapplication

import org.junit.Test

class ClassTest {
    val testNum = SealedTestClass.Num(5)
    val testSum = SealedTestClass.Sum(5,5)
    val test = RunToClass("tlsvlfwp1@naver.com")
    val testA = RunToClass("tlsvlfwp1@naver.com")

    @Test
    fun sealedTest(){
        test.classTest(testNum)
        println(test.equals(testA))
    }

}