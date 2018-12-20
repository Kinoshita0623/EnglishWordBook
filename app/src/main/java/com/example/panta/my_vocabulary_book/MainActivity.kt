package com.example.panta.my_vocabulary_book

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.*
import com.example.panta.my_vocabulary_book.CSVReader.CSVReader
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    data class HaveList(val list: List<String>):Serializable

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val checked = linkedMapOf("chu1" to false, "chu2" to false, "chu3" to false, "kou1" to false, "kou2" to false, "kou3" to false)

        val checkBoxMap = mapOf(
            "chu1" to findViewById<CheckBox>(R.id.chu1),
            "chu2" to findViewById(R.id.chu2),
            "chu3" to findViewById(R.id.chu3),
            "kou1" to findViewById(R.id.kou1),
            "kou2" to findViewById(R.id.kou2),
            "kou3" to findViewById(R.id.kou3)
        )

        var questionTypeNumber = 0
        val questionType = findViewById<RadioGroup>(R.id.questionType)
        questionType.setOnCheckedChangeListener { group, checkedId ->
            val button: RadioButton = findViewById(checkedId)
            questionTypeNumber = when(button.id){
                R.id.upRadio -> 0
                R.id.downRadio -> 1
                else -> -1

            }
        }

        val startPracticeButton = findViewById<Button>(R.id.startButton)
        val findAllButton = findViewById<Button>(R.id.findAllButton)

        startPracticeButton.setOnClickListener{
            val intent = Intent(applicationContext, PracticeActivity::class.java)
            val list = checked.filter{i ->
                i.value
            }.keysList()
            Log.d("Intent開始前リストデバッグ",list.toString())
            if(list.isNullOrEmpty()){
                AlertDialog.Builder(applicationContext).apply{
                    setTitle("エラー")
                    setMessage("出題学年を一つ以上選択してください")
                    setPositiveButton("OK",null)

                }.show()
            }else{
                val listData = HaveList(list)
                intent.putExtra("selected",listData)
                intent.putExtra("questionType",questionTypeNumber)
                startActivity(intent)
            }

        }

        findAllButton.setOnClickListener{

        }

        checkBoxMap.forEach { check ->
            check.value.setOnClickListener{
                checked[check.key] = !checked[check.key]!!
            }
        }


    }
}

fun<T,E> Map<T,E>.keysList():List<T>{
    val list = ArrayList<T>()
    this.forEach{
        list.add(it.key)
    }
    return list
}

