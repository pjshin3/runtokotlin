package com.example.myapplication

import kotlinx.coroutines.*

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

    fun dispartureTest() = runBlocking<Unit> {
        // 만약 인자값이 없이 launch를 하면
        // 부모의 context, dispatcher를 상속받는다
        // dispartchers를 지정하지 않으면 내부적으로 GlobalScope를 사용하고 continuation을 suspend상태로 생성되고
        // resume요청이 왔을때 현재 disparcher에게 dispatch(스래드 전환)이 필요한지 판단하여 적합한 스래드로 전달하여 수행된다.
        // 위의 내용을 토대로 1초마다 100_000번 점을찍는 스래드를 사용해도 OOM(OutOfMemory)이 발생하지 않는다.
        // CPS
        launch {
            println("메인 쓰래드  UI 작업에 해당하는 작업을 진행한다.        ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined 쓰래드   정해지지 않은 쓰래드 기본적으로 메인스레드에서 동작함.       ${Thread.currentThread().name}")
        }

        //쓰래드 풀에서 사용하는 것과 같음.
        //GLobalScope.launch와 같음 (애플리케이션 종료되지 않으면 실행할 내용있다면 계속해서 실행을 함.)
        //ForkJoinPool 의 개념
        //큰 업무를 작은 단위의 업무로 쪼개고 이 작은 단위를 fork하여 업무를 수행을 반복
        //더이상 포크가 일어나지 않는다면 join을 통해 작업물들을 취합하여 결과를 완성한다.
        launch(Dispatchers.Default) {
            println("Default 쓰래드  디폴트 쓰래드는 기본적으로 백그라운드에서 작업량이 많은 형태의 작업을 진행한다.  ${Thread.currentThread().name}")
        }
        //코루틴 의도와 다르게 스래드를 새로 띄우는 비용이 들고, 사용이후 해제를 해줘야함.
        launch(newSingleThreadContext("MyOwnThread")) {
            println("newSingleThreadContext        ${Thread.currentThread().name}")
        }
    }

    //oom 테스트
    fun oomTest() = runBlocking {
        repeat(100_000){
            launch {
                delay(1000L)
                println(". $it")
            }
        }
    }
}