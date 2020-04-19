package com.example.myapplication

class Person(val name : String, val age : Int)

class Lamda {
    private val list = listOf(1,2,3,4)
    private val personList = listOf(
        Person("shin",30),
        Person("kim",59),
        Person("Hong",30),
        Person("Jang",26)
    )
    fun main(){
//        A()
//        B()
//        C()
        D()
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
        )
    }
}