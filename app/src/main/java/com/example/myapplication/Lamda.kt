package com.example.myapplication



class Person(val name : String, val age : Int)

class Lamda {
    val list = listOf(Person("shin",30), Person("Kim",56))

    fun main(){
        println(list.maxBy(Person::age))
        println(list.maxBy{it.age})
    }
}