package com.example.myapplication

import java.lang.StringBuilder


class HighFuntion {

    fun A(item:(Int,Int) -> Int) {
        val result = item(2,3)
        println("result is $result")
    }


    //고차함수 인자로 받는 아이탬 중에 현재 문자열에 있는 값이 포함되어 있는 문자만 다시 문자열로 반환한다.
    fun String.B(item: (Char) -> Boolean){
        val result = StringBuilder()

        for (index in 0 until length){
            val element = get(index)
            if (item(element)){
                result.append(element)
            }
        }

        println("result is ${result.toString()}")
    }


    fun main(){
//        A { a,b -> a*b }
//        val itme = "abcdef134"
//        itme.B { it in 'a' .. 'z' }
    }


}
