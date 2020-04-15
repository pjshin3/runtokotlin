package com.example.myapplication

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.*
import java.lang.Exception
import java.net.Socket

class SoketClass{

    fun connectNist() = runBlocking(Dispatchers.IO) {
        var sock : Socket? = null
        var result = arrayListOf<Char>()
        launch {
            try {
                // 주소와 포트를 통해 소켓 연결
                sock = Socket("dict.org",2628)
                sock?.let {
                    //접속 타임아웃 지정
                    it.soTimeout = 15000
                    //내가 쓸 스트림의 크기를 연결된 소켓에서 가져온다.
                    val outst = it.getOutputStream()
                    //내가 데이터를 정의에 맞게 쓸 수 있도록 Writer를 정의
                    val writer = OutputStreamWriter(outst,"UTF-8")
                    //내가실제 보낼 데이터
                    writer.write("DEFINE eng-lat gold\r\n")
                    //버퍼가 다 차지 않아도 해당 현재 버퍼를 보내겠다는 기능.
                    writer.flush()

                    // 서버로 부터 내려주는 스트림의 크기를 지정
                    val intst = it.getInputStream()
                    // 서버가 내려준 데이터를 읽어주는 Reader 를 정의
                    val reder = BufferedReader(InputStreamReader(intst,"UTF-8"))
                    for (i in reder.readLine()){
                        result.add(i)
                    }

                    //dict는 quit라는 소켓 데이터를 받아야 서버에서 닫는다.
                    writer.write("quit\r\n")
                    writer.flush()
                    println(result)
                }


            }catch (e: Exception){
                println(e)
            }finally {
                sock?.close()
            }
        }
    }

}