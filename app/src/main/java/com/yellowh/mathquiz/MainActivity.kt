package com.yellowh.mathquiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yellowh.mathquiz.databinding.ActivityMainBinding
import com.yellowh.mathquiz.db.AnswerDao
import com.yellowh.mathquiz.db.AppDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var answerDao: AnswerDao


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MathQuiz)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        answerDao = db.getAnswerDao()

        Thread {
            answerDao.allDelete()
        }.start()

        binding.plus.setOnClickListener {
            nextPage("+")
        }

        binding.minus.setOnClickListener {
            nextPage("−")
        }

        binding.multiply.setOnClickListener {
            nextPage("×")
        }

        binding.division.setOnClickListener {
            nextPage("÷")
        }

    }

    private fun nextPage(symbol: String) {
        val intent = Intent(this, LevelChoiceActivity::class.java)
        intent.putExtra("symbol", symbol)
        startActivity(intent)
        finish()
    }
}