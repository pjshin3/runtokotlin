package com.example.myapplication

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.lang.ArithmeticException
import kotlin.system.measureTimeMillis

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

    // try / finally 문에서는 잡이 취소 될경운 cancelledException이 발생될경우 fianlly문이 실행이 된다.
    // 잡이 이미 끝난 상태에서 중단함수인 delay를 사용할 경우 다시 cancelledException이 발생하여 finally문이
    // 실행되지 않고 끝이난다. 하지만 이를 해결할 수 있는 방법이 withContext에 NonCancellable을 인자를 넘겨 처리방법이 가능하다.
    // Cancellable의 내부는 isActive가 항상 true이기때문에 가능하다.
    // 위의 내용이외에 또한 동작쓰래드도 변경이 가능하다
    // Main, Io , Defult 등등 ..
    fun cancelationTest() = runBlocking {
        val job = launch {
            try {

                repeat(1000) {i ->
                    println("i am sleeping $i ...")
                    delay(500L)
                }

            }finally {
                withContext(NonCancellable){
                    delay(1000)
                    println("main : i am running finally!!")
                }
            }

        }
        delay(1300L)
        println("main i am tired of waiting!")
        job.cancelAndJoin()
        println("main : now i can quit !")
    }

    //채널을 통해 각 잡에서 하나의 값을 공유할 수 있다.
    fun channelTest() = runBlocking {
        val channel = Channel<Int>()

        launch {
            for (x in 1..5){
                channel.send(x * x)
            }
        }
        repeat(5) { println(channel.receive())}
    }

    // 확장함수와 프로듀스를 통해 채널에 대한 비지니스로직을 더 입맛에 맞게 만들수 있다.
    // 파이프라인 패턴을 사용할때 유용함.
    // 파이프라인이란 하나의 코루틴이 생상해낸 스트림을 다른 코루틴이 이 스트림을 받아 작업 수행후 가공된 결과를 내는것.
    fun produceTese() = runBlocking {
        val square = produceSquares(5)
        val double = produceDouble(square)

        double.consumeEach { println(it) }
    }

    fun CoroutineScope.produceSquares(max: Int) : ReceiveChannel<Int> = produce{

        for (x in 1..max){
            send(x * x)
        }
    }
    fun CoroutineScope.produceDouble(number: ReceiveChannel<Int>) : ReceiveChannel<Int> = produce {
        number.consumeEach { send(it * 2) }
    }

    //파이프라인으로 소수 만들기
    fun pipelineTest() = runBlocking {
        var cur = numbersFrom(2)
        for (i in 1..10){
            val prime = cur.receive()
            println("prime = $prime")
            cur = filter(cur,prime)
        }
        coroutineContext.cancelChildren()
        println("done")
    }
    fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
        var x = start
        while (true) send(x++)
    }
    fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
        for (x in numbers){
            if (x % prime != 0){
                send(x)
            }
        }
    }

    //순차 실행
    fun sequentialTese() = runBlocking {
        val time = measureTimeMillis {
            val a = A()
            val b = B()

            println("The answer ${a + b}")
        }
        println("Completed in $time ms")

    }
    suspend fun A() : Int{
        delay(1000L)
        return 53
    }
    suspend fun B() : Int{
        delay(3000L)
        return 32
    }
    //동시 실행
    fun concrurrentTest() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val a = async {  A() }
            val b = async { B() }
            println("The answer ${a.await()+b.await()}")
        }
        println("Completed in $time ms")

    }


    fun exceptionTese() = runBlocking{
        try {
            val time = measureTimeMillis {
                println("시작 ${concurrentSum()}")
            }
        }catch (throwable : Throwable){
            println("실패 $throwable")
        }
    }

    // 호출한쪽의 부모까지 호출하게 된다.
    // 같은 스코프안에서 하나의 자식스레드가 실패하더라도 모두 취소되고 호출한쪽에 알리게됨.
    suspend fun concurrentSum() : Int = coroutineScope {
        val one = async {
            try {
                delay(Long.MAX_VALUE)
                A()
            }finally {
            }
        }
        val two = async<Int> {
            println("sencond chid thow an exception")
            B()
            throw ArithmeticException("Exception on purpose")
        }
        one.await() + two.await()
    }

    //각각의 빌더에 리턴 값 및 async vs slaunch의 비교
    fun returnTese() = runBlocking {
        val jop = launch(CoroutineName("TEST1")) {
            println("${Thread.currentThread().name} My builder is job , job is noting return")
        }

        val deferred =  async(CoroutineName("TEST2")) {
            println("${Thread.currentThread().name} My builder is deferred, deferrd is T return")
            "this is value"
        }
        println(deferred.await())
        println(jop.join()) // Unit은 아무것도 아니다. 코틀린 전용 리턴값

        //쓰래드 변경 테스트
        //use() 메소드는 더 이상 쓰래드가 필요하지 않을경우 자동으로 해제해준다.
        newSingleThreadContext("cx1").use { cx1 ->
            newSingleThreadContext("cx2").use { cx2->
                runBlocking(cx1) {
                    println("${Thread.currentThread().name} 첫번쨰")
                    withContext(cx2){
                        println("${Thread.currentThread().name}  두버쨰")
                    }
                    println("${Thread.currentThread().name} 세번째")
                }
            }
        }
    }
}