package com.example.panta.my_vocabulary_book

import android.content.Context
import android.util.Log
import com.example.panta.my_vocabulary_book.CSVReader.CSVReader
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.coroutineContext

class QuestionController(private val context: Context){

        private val random = Random()

        fun getQuestion(wordsList:List<WordData>):WordData{
            val randomNumber = random.nextInt(wordsList.size)
            return wordsList[randomNumber]
        }

        fun getWordsList(schoolYearList: List<String>):List<WordData>{

            //val listMap = HashMap<String, List<WordData>>()
            val list = ArrayList<WordData>()

            val reader = CSVReader(context = context)

            schoolYearList.forEach{

                list.addList(multipleListConverter(reader.readCsv(it)))

            }
            Log.d("デバッグ",list.size.toString())
            Log.d("引数デバッグ",schoolYearList.size.toString())
            return list
            //return listMap

    }

    private fun multipleListConverter(list: List<List<String>>):List<WordData>{

        Log.d("コンバーター引数デバッグ",list.size.toString())

        val wordList = ArrayList<WordData>()
        var part = ""
        list.forEach{it->

            val iterator = it.iterator()
            var counter = 0
            var index = 0
            var jp = ""
            var en = ""

            while(iterator.hasNext()){
                when(counter){
                    0 ->{
                        index = Integer.parseInt(iterator.next())
                    }
                    1 ->{
                        jp = iterator.next().replace("%2C",",")
                    }
                    2 ->{
                        en = iterator.next()
                    }
                }
                counter++
            }

            if(counter <= 2){
                part = jp
            }else{
                val wordData = WordData(index = index, jpWord = jp, enWord = en, part = part)
                wordList.add(wordData)
            }

        }

        return wordList
    }
}

data class WordData(val index:Int, val jpWord:String, val enWord:String, val part:String)

fun<T> ArrayList<T>.addList(list:List<T>):ArrayList<T>{
    list.forEach{
        this.add(it)
    }
    return this
}
