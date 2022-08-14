package com.yellowh.mathquiz

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yellowh.mathquiz.databinding.ActivityResultBinding
import com.yellowh.mathquiz.db.AnswerDao
import com.yellowh.mathquiz.db.AppDatabase
import com.yellowh.mathquiz.function.countReset
import com.yellowh.mathquiz.function.getOCount
import com.yellowh.mathquiz.function.getXCount

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    lateinit var db: AppDatabase
    private lateinit var answerDao: AnswerDao
    var symbol = ""
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        answerDao = db.getAnswerDao()


        if (intent.hasExtra("symbol")) {
            symbol = intent.getStringExtra("symbol").toString()
            level = intent.getIntExtra("level", 1)
        }

        binding.rightCount.text = getOCount().toString()
        binding.wrongCount.text = getXCount().toString()

        countReset()

        binding.replay.setOnClickListener {
            val intent = Intent(this, CalculationActivity::class.java)
            intent.putExtra("symbol", symbol)
            intent.putExtra("level", level)
            startActivity(intent)
            deleteAllAnswerList()
            finish()
        }

        binding.redo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            deleteAllAnswerList()
            finish()
        }

        binding.btnNextPage.setOnClickListener {
            val intent = Intent(this@ResultActivity, WrongAnswerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                exitMessage()
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    private fun exitMessage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Math Quiz 종료")
        builder.setMessage("종료하시겠습니까?")
        builder.setNegativeButton("취소", null)
        builder.setPositiveButton(
            "확인"
        ) { _, _ ->
            deleteAllAnswerList()
            finish()
        }
        builder.show()
    }

    private fun deleteAllAnswerList() {
        Thread {
            answerDao.allDelete()
        }.start()
    }
}