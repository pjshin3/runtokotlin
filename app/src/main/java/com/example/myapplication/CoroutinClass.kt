package com.example.myapplication

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutinClass {



    fun main(){
        //coroutin()
        deffient()
    }

    // runblocking 주어진 블록에 대해 블록이 완료될때 까지 스래드를 멈추있는 코루틴 빌더
    fun coroutin() = runBlocking  {
            launch {
                testSuspendFuntion()
            }
            println("Hallo")
    }
    // 코루틴 내부에서 중단 함수를 사용하는 함수에 대해선 suspend 키워드가 함수명 앞에 붙어 야함.
    suspend fun testSuspendFuntion(){
        delay(1000)
        println("World")
    }


    //runbolcking은 블록자체를 자식들이 모두 끝날때까지 블록 함.
    //coroutinscope는 모든 자식들이 모두 끝날때까지 블록하지 않음
    fun deffient() = runBlocking {

        launch {
            delay(200L)
            println("Task from runblocking")
        }

        coroutineScope {
            launch {
                delay(500L)
                println("Task from nested launch")
            }
            delay(100L)
            println("Task from coroutine scope")
        }
        println("Coroutine scope is Over")
    }

}