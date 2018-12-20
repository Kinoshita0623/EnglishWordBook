package com.example.panta.my_vocabulary_book

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class PracticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        val intent = intent
        val data = intent.getSerializableExtra("selected")
        val questionType = intent.getIntExtra("questionType",0)
        val jpView = findViewById<TextView>(R.id.japaneseText)
        val enView = findViewById<TextView>(R.id.englishText)
        val partView = findViewById<TextView>(R.id.partView)

        val questionController = QuestionController(applicationContext)
        val wordsList : List<WordData> = if(data is MainActivity.HaveList){
            Log.d("スマートキャスト成功",data.toString())
            var wordsList = questionController.getWordsList(data.list)
            Log.d("デバッグ",wordsList.size.toString())
            wordsList

        }else{
            throw Exception("キャストエラーが発生しました")
        }

        Log.d("問題タイプ",questionType.toString())


        fun setTextToDisplay(enText: String, jpText: String, part: String){
            enView.text = enText
            jpView.text = jpText
            partView.text = part
        }

        fun isShow(isEnShow: Boolean, isJpShow: Boolean, isPartShow: Boolean){
            enView.visibility = if(isEnShow){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }

            jpView.visibility = if(isJpShow){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }

            partView.visibility = if(isPartShow){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }

        }

        fun questionTypeJdg(type: Int = questionType){
            Log.d("出題形式ナンバー",type.toString())
            if(type > 0){
                isShow(false,true,false)
            }else{
                isShow(true,false,false)
            }
        }

        fun allShow(){
            isShow(true,true,true)
        }

        val watchAnswerButton: Button = findViewById(R.id.watchAnswerButton)
        val nextButton: Button = findViewById(R.id.nextButton)
        val firstQuestion = questionController.getQuestion(wordsList)
        setTextToDisplay(enText = firstQuestion.enWord, jpText = firstQuestion.jpWord, part = firstQuestion.part)
        questionTypeJdg()

        var answerFlag = false
        watchAnswerButton.setOnClickListener {
            if(answerFlag){
                questionTypeJdg()
            }else{
                allShow()
            }
        }

        nextButton.setOnClickListener {
            val question = questionController.getQuestion(wordsList)
            setTextToDisplay(enText = question.enWord, jpText = question.jpWord, part = question.part)
            questionTypeJdg()
        }

    }
}
