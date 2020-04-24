package com.example.myapplication

import kotlinx.coroutines.Runnable
import java.lang.StringBuilder

class Person(val name : String, val age : Int)

class Lamda {
    private val list = listOf(1,2,3,4)
    private val personList = listOf(
        Person("shin",30),
        Person("kim",59),
        Person("Hong",30),
        Person("Jang",26),
        Person("Jang",26)
    )
    fun main(){
//        A()
//        B()
//        C()
//        D()
//        E()
//        F()
//        G()
//        H()
        I()
    }

    //컬렉션 API filter 술어 함수
    //술어 함수란 = 참/거짓을 반환하는 함수
    //원소를 변환할 수 없고 해당 조건에 대한 원소를 제거하고 반환시킨다.
    fun A(){
        println(list.filter { it % 2 == 0 })
    }
    //map
    //원소를 변환살 수 있다.
    fun B(){
        println(list.map { it * it })
    }
    //복합 사용
    // Person::name = 위임,참조
    fun C(){
        println(
            personList.filter { it.age == 30  }
                .map { Person::name }
        )
    }
    // 술어 API
    // all, any, count, find
    fun D(){
        val condition = {p : Person -> p.age <= 27}

        //모든 아이템이 인자에 해당하는 조건이 맞는지 확인.
        println(
            personList.all(condition)
            //직접 람다를 넣을 수 있음
            //personList.all { it.age <= 27 }
        )
        //하나라도 조건이 맞는지 확인.
        println(
            personList.any(condition)
        )
        //조건에 해당하는 원소가 몇개인지 카운트 값을 반환한다.
        println(
            personList.count(condition)
        //personList.filter(condition).size
        //위의 라인과 똑같은 작업은 하지만 count가 훨씬 효율적이므로 적재적소에 잘 사용해야함.
        )
        //조건에 맞는 원소를 찾아 객체로 반환한다.
        println(
            personList.find(condition)
        // personList.firstOrNull(condition)
        //find는 마찬가지로 조건에 맞는 것이 없는 경우 null을 반환하지만,
        //위와 같이 가독성을 위해 좀더 명확하게 firstOrNull으로 사용이 가능하다.
        )
        //원소로 분류할 수 있다.
        println(
            personList.groupBy { it.age }
        )
    }
    // 중첩된 원소처리 API
    fun E(){
        val list = listOf("def","abc","abc")
        //문자열을 문자로 나열해준다.
        println(
            list.flatMap { it.toList() }
        )
    }
    // 연쇄 계산을 할경우 시퀀스를 이용해 하나의 결과 값을 받을 수 있다.
    // 증긴연산, 최종 연산등 중간연산의 결과값으로 다음의 연쇄 계산을 할 수 있어
    // 중복 루프를 통한 어떤 계산을 할 경우 필요할 것 같다.
    fun F(){
        //지연 연산
        //asSequence는 최종 호출이 있을경우에만 결과를 출력한다.
        //중간 연산들은 최종호출이 호출 될때 지연됫던 연산을 하여 결과값을 반환한다.
        //이경우 최종 연산이 없을 경우 결과값을 반환하지 않는다.
        println(
            personList.asSequence()
                .filter{it.age > 30} // 중간 연산
                .map { it.age * it.age } // 중간 연산
                .toList() // 최종 연산
        )
        println(
            list.asSequence()
                .map { it * it }
                .find { it > 3 }
        )
        //시퀀스를 직접 만들 수 있다.
        val stepA = generateSequence(0) {it + 1}
        val stepB = stepA.takeWhile { it <= 100 }
        println(
            stepB.sum()
        )
    }
    // 자바에 선언부가 있을 경우 코틀린에서는 런어블과 같은 인터페이스를 람다로 넘길 수 있음.
    // 자동으로 런어블로 인스턴스화해줌.
    fun Gtest(delay: Int, computation:java.lang.Runnable){}
    fun G(){
        // 명시적 객체 생성
        // 매번 새로 만들어서 호출된다.
        Gtest(1000,object : Runnable{
            override fun run() {
                println("runnable은 매번 새로 생성된다.")
            }
        })
        // 위와 같은 내용을 풀어 쓴내용
        val run = Runnable { println("매번 같은 객체를 참조한다.") }
        Gtest(1000,run)


        // 이경우 단한번만 인스턴스를 만들어 호출될때 반복적으로 사용된다.
        // 단 람다를 인자로 넘기는 구현부에서 주변 변수를 람다가 포획할 경우 매번 새롭게 인스턴스화를 한다.
        //Gtest(1000){ println("테스트")}
    }
    // 람다 수신 객체 함수(지정 함수)
    // with, apply
    fun H(){
        //with
        //인스턴스로 넘긴 객체의 메소드를 자신을 호출할 필요없이 메소드를 이용이 가능하다.
        //따로 반환하지 않는다.
        val resultWith = with(StringBuilder()){
            for (letter in 'A' .. 'Z'){
                append(letter)
            }
            append("\nNow I Know the alphabat !!")
            toString()
        }
        println(resultWith)

        //with와 거의 비슷하지만 apply는 해당 객체를 apply블록안에서 초기화한후 해당 객체를 반환한다.
        println(
            StringBuilder().apply {
                for (letter in 'A' .. 'Z'){
                    append(letter)
                }
                append("\nNow I Know the alpahbat !!")
            }.toString()
        )

        //buildString은 StringBuilder 객체를 만들고 또 다시 문자열로 만드는 메소드를 따로 실행하지 않아도
        //두가지 일을 모두 해준다.
        //StringBuilder를 이용하여 문자열을 만들때 가장 우아한 해법이다.
        println(
            buildString {
                for (letter in 'A' .. 'Z'){
                    append(letter)
                }
                append("\nNow I Know the alpahbat !!")
            }
        )
    }
    // 클로저라는 개념은 익명함수 람다가 외부의 변수를 참조하는 것을 말한다.
    fun I(){
        var list = mutableListOf<Int>(1,2,3,4,5,6,7,8,9,0)
        var item = 0
        list.filter { it > 0 }.forEach {
            item += it
        }
        println(item)
    }
}