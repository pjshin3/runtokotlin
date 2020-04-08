package com.example.myapplication

class GenaricTestClass {
    //E = 컬렉션에 저장되는 요소
    fun <E> testEntity(_itme : List<E>) {
        val item = _itme.first()
        println(item)
    }
    // N = 숫자타입 Int와 같은 Number의 서브 클래스
    fun <N :Number> testNumber(_item : N){
        val item = _item
        println(item)
    }
    // T =모든 타입
    fun <T> testT(_item : T){
        val item = _item
        println(item)
    }
    // K = 맵과같은 키와 벨류 쌍으로 저장되는 타입의 키
    // V = 맵과 같은 키와 벨류 쌍으로 저장되는 타입의 벨류
    fun <K,V> testMap(_item: Map<K,V>){
        val item = _item
        println(item.values.first())
    }
//    // R = 함수 리턴타입
//    fun <R,T> tsetReturn(_itmes : (T)-> R): R{
//        val item = _itmes
//
//    }
}