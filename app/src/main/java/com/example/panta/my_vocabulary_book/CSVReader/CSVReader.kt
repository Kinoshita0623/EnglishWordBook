package com.example.panta.my_vocabulary_book.CSVReader
import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.async
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class CSVReader(private val context: Context){

    fun readCsv(fileName:String,context: Context = this.context):ArrayList<List<String>>{

        val list = ArrayList<List<String>>()

        val assetManager = context.resources.assets
        try{
            val inputStream = assetManager.open("$fileName.csv")
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line:String? = bufferedReader.readLine()
            while(line  != null){
                val rawData = line.split(",")
                list.add(rawData)
                line = bufferedReader.readLine()
            }
        }catch(e: IOException){
            e.printStackTrace()
        }
        return list

    }



}

