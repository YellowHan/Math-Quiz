package com.yellowh.mathquiz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yellowh.mathquiz.databinding.ActivityFrameBinding
import com.yellowh.mathquiz.db.AnswerDao
import com.yellowh.mathquiz.db.AnswerEntity
import com.yellowh.mathquiz.db.AppDatabase
import com.yellowh.mathquiz.fragment.Fragment_cal
import com.yellowh.mathquiz.fragment.Fragment_div
import com.yellowh.mathquiz.function.*
import java.time.LocalDate


class CalculationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrameBinding
    private lateinit var db: AppDatabase
    private lateinit var answerDao: AnswerDao
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        answerDao = db.getAnswerDao()

        if (intent.hasExtra("level")) {
            level = intent.getIntExtra("level", 1)
        }

        if (intent.hasExtra("symbol")) {
            inputNumber(intent.getStringExtra("symbol").toString())
        }

        if (symbol == "÷") {
            divisionInfo()
        }

        binding.check.setOnClickListener {
            StrokeManager.recognize(this)
        }

        binding.cancel.setOnClickListener {
            binding.drawingView.clear()
            StrokeManager.clear()
        }

        binding.btnBack.setOnClickListener {
            countReset()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnPracticeClear.setOnClickListener {
            binding.practiceView.clear()
        }

    }

    fun showO(context: CalculationActivity) {
        context.binding.o.visibility = View.VISIBLE
    }

    fun showX(context: CalculationActivity) {
        context.binding.x.visibility = View.VISIBLE
    }

    fun nextView() {
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                nextPage()
            }
        }, 500)
    }

    private fun nextPage() {
        binding.o.visibility = View.INVISIBLE
        binding.x.visibility = View.INVISIBLE
        binding.drawingView.clear()
        binding.practiceView.clear()
        StrokeManager.clear()
        userCount()

        val currentCount = getCount()

        if (currentCount > 10) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("symbol", symbol)
            intent.putExtra("level", level)
            startActivity(intent)
            finish()
        }

        binding.currentCount.text = currentCount.toString()

        inputNumber(symbol)

    }

    private fun random(number: String): Int {
        val random = java.util.Random()

        if (level == 1) {
            return random.nextInt(9) + 1
        }

        if (level == 2) {
            if (number == "first") {
                return random.nextInt(90) + 10
            }
            return random.nextInt(9) + 1
        }

        if (level == 3) {
            return random.nextInt(90) + 10
        }

        if (level == 4) {
            if (number == "first") {
                return random.nextInt(900) + 100
            }
            return random.nextInt(90) + 10
        }
        return 0
    }

    private fun inputNumber(symbol: String) {
        var firstNum = random("first")
        var secondNum = random("second")

        Log.d("로그", "문제 : $firstNum $symbol $secondNum")

        if (symbol == "−" || symbol == "÷") {
            if (firstNum < secondNum) {
                val tmp: Int = firstNum
                firstNum = secondNum
                secondNum = tmp
            }
        }

        // 프래그먼트에 데이터 전달
        val bundle = Bundle()
        bundle.putString("firstNum", firstNum.toString())
        bundle.putString("secondNum", secondNum.toString())
        bundle.putString("symbol", symbol)

        fragSet(symbol)

        calculation(firstNum, secondNum, symbol)
    }

    fun insertDb() {
        Thread {
            answerDao.insertAnswer(
                AnswerEntity(
                    null, firstNumber, secondNumber, symbol, result,
                    StrokeManager.getUserNumber()
                )
            )
        }.start()
    }

    private fun fragSet(symbol: String) {
        val ft = supportFragmentManager.beginTransaction()
        if (symbol == "÷") {
            ft.replace(R.id.fragment_frame, Fragment_div()).commit()
        } else {
            ft.replace(R.id.fragment_frame, Fragment_cal()).commit()
        }
    }

    private fun divisionInfo() {
        val dateNow = LocalDate.now()
        if (date != dateNow.dayOfMonth) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("나눗셈 정답 입력 방법")
                .setMessage("나머지가 0일 경우 : 몫만 입력 \n나머지가 0보다 클 경우 : \n몫 ... 나머지로 입력\n예)2 or 2...3")
                .setPositiveButton("확인", null)
                .setNegativeButton(
                    "오늘 하루 그만 보기"
                ) { _, _ -> noTodaySet() }
            builder.show()
        }
    }

    private fun noTodaySet() {
        val dateNow = LocalDate.now()
        date = dateNow.dayOfMonth
    }
}