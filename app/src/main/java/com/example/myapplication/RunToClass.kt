package com.example.myapplication

sealed class SealedTestClass{
    class Num (val count : Int): SealedTestClass()
    class Sum (val countA : Int , val countB : Int ): SealedTestClass()
}
// 무조건 상속해야함.
abstract class A(val item : String){
    open fun itemPrint(){
        println(item)
    }
}
interface old {
    var item : String
}

// 상속 안됨
class B(item: String) : A(item) {
    override fun itemPrint() {
        super.itemPrint()
    }
}

interface test{
    var nickname : String
}


class RunToClass(val email : String) : test {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is RunToClass)
            return false

        return email == other.email
    }

    override var nickname: String = "angimoTi"
        set(value : String){
            println("이전에 값은 $nickname 입니다. 이제 새로운 값은 =$value 입니다.")
            field = value
        }

    fun classTest(e : SealedTestClass){

        nickname = email.substringBefore("@")
        println(nickname)

        when(e){
            is SealedTestClass.Num -> println(e.count)
            is SealedTestClass.Sum -> println(e.countA + e.countB)
        }
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + nickname.hashCode()
        return result
    }
}