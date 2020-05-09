package com.example.myapplication

import io.reactivex.Observable
import org.reactivestreams.Publisher
import java.lang.Thread.sleep

class Rx {
    private val listIntArray = arrayOf(1,2,3,4,5,6,7,8,9)
    private val listInStringArrayList = arrayListOf("Shin,Hong,Kim,Gang")
    fun main(){
        //A()
        //B()
//        C()
//        D()
//        E()
//        F()
        CC()
    }

    private val item = 8
    fun AA() =
        Observable.range(1,9)
            .map {range ->
                "$item * $range = ${item*range}"
            }
            .subscribe(::println)

    fun BB()=
        Observable.range(1,200)
            .filter{ it % 10 == 0 }
            .subscribe(::println)

    fun CC() =
        Observable.range(1,10)
            .reduce{ item1,item2 -> item1+item2 }
            .subscribe(::println)

    // JUST
    // 타입이 모두 같고 최대 처리가 가능한 데이터는 10개
    // 인자로 넣은 값을 차례로 발행한다.
    // 인자값을 모두 발행하면 Observar는 자동으로 dispose를 실행하여 구독을 더이상 받지않는다.
    fun A(){
        val item = Observable.just(1,2,3,4,5,6,7,8,9,3)
        val observer = item.subscribe(::println)
    }
    // CREATE
    // onNext , onComplete , onError등을 모두 개발자가 직접적으로 설계를 해줘야한다.
    // onNext = 값을 발행
    // onComplete = 모든 값을 발행
    // onError = 발행중 에러
    fun B(){
        val item = Observable.create<Int> {
            it.onNext(1000)
            it.onNext(5000)
            it.onNext(10000)
            it.onComplete()
        }
        val observer = item.subscribe(::println)
    }
    // FROMARRAY
    // 배열 원소를 하나씩 출력해준다.
    // 원소를 출력하기 위해서 *을 붙쳐주는대 이는 명시적 래퍼타입 Intiger가 아닌 원소타입 int형태의 값을 읽기위해 붙쳐준다.
    fun C(){
        val item = Observable.fromArray(*listIntArray)
        val observer = item.subscribe(::println)
    }
    //FROMITERABLE
    //이터러블을 상속받은 모든 컬렉션들에 대한 원소값을 가져올 수 있다.
    //기본적으로 상속받은 자료구조는 list,set,Queue 이다.
    fun D(){
        val item = Observable.fromIterable(listInStringArrayList)
        val observer = item.subscribe(::println)
    }
    //FROMCALLABLE
    //Runnable 스래드 처리 이후 리턴 값이 없다
    //Callable 스래드 처리 이후 리턴 값이 있다.
    // fromCallable에서는 처리된 callable에 리턴 값을 발행한다.
    val Etest = {
        sleep(5000)
        "hallo world"
    }
    fun E(){
        val item = Observable.fromCallable(Etest)
        val observer = item.subscribe(::println)
    }
    //PUBLISHER
    //다른 메소드와 달리 데이터를 생성에서 발행까지 모두 할 수 있다.
    val Ftest = Publisher<String>{
        it?.onNext("hello world")
        it.onComplete()
    }
    fun F(){
        val item = Observable.fromPublisher(Ftest)
        val observer = item.subscribe(::println)
    }
    //Single vs Observable 객체 차이
    //Single = 이름과 같이 데이터를 한번 발행하면 종료된다. 서버 Api에 적합
    //Observeble = 데이터를 여러게를 발행할 수 있다.
    fun G(){
//        val observable = Observable.just(1,2,3,4,5,6,7,8)
//        val single = Single.just(1,2,3,4,5,6,7,8,8,9) 오류문구가 난다.
    }
}