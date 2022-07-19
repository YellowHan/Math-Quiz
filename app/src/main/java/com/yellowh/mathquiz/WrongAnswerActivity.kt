package com.yellowh.mathquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yellowh.mathquiz.databinding.ActivityWrongAnswerBinding
import com.yellowh.mathquiz.db.AnswerDao
import com.yellowh.mathquiz.db.AnswerEntity
import com.yellowh.mathquiz.db.AppDatabase

class WrongAnswerActivity : AppCompatActivity() {

    lateinit var binding: ActivityWrongAnswerBinding
    lateinit var adapter: AnswersAdapter
    lateinit var answerList: ArrayList<AnswerEntity>
    lateinit var db: AppDatabase
    lateinit var answerDao : AnswerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWrongAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        answerDao = db.getAnswerDao()

        getAllAnswerList()

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun getAllAnswerList() {
        Thread {
            answerList = ArrayList(answerDao.getAll())
            setRecycleView()
        }.start()
    }


    private fun setRecycleView() {
        runOnUiThread {
            adapter = AnswersAdapter(answerList)
            binding.rvWrongList.adapter = adapter
            binding.rvWrongList.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllAnswerList()
    }
}