package com.example.myapplication

class Helloworld {

    fun test(){
        println("test good")
    }
    fun contains369(num : Int):Boolean{
        listOf("3","6","9").forEach {
            if (num.toString().contains(it)){
                return true
            }
        }
        return false
    }
}