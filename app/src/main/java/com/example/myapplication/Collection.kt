package com.example.myapplication

class Collection {
    //읽기 리스트 초기화
    val listRead = listOf("Hong","Shin","Kim")
    //읽기 쓰기 리스트
    var listReadWrite = mutableListOf("Hong","Shin","Kim")


    fun listTest(){
        println("=====================list 라인===========================")

        listRead.contains("Hong").let { if (it) println("in item") else println("Noting") }
        println(listRead.getOrElse(4){"Noting Else"})


        for (item in listRead){
            println(item)
        }

        (listRead as MutableList)[1] = ("shin")

        listRead.forEach {item ->
            println(item)
        }

        listRead.forEachIndexed{ index, item ->
            println("$index,$item")
        }

        listReadWrite.add("Jang")
        val (item, _, item2,item3) = listReadWrite
        println("$item,$item2,$item3")

    }

    val setRead = setOf("Hong","Shin","Shin")
    var setReadWrite = mutableSetOf("Hong","Shin","Shin")

    fun setTest(){
        println("=====================set 라인===========================")
        println(setRead)
        setReadWrite.add("Hong")
        println(setReadWrite)
    }

}