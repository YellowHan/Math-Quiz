package com.yellowh.mathquiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yellowh.mathquiz.databinding.ItemListBinding
import com.yellowh.mathquiz.db.AnswerEntity

class AnswersAdapter(private val answerList: ArrayList<AnswerEntity>) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    var count = 1

    inner class ViewHolder(binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        var count = binding.numberCount
        val firstNumber = binding.number1
        val secondNumber = binding.number2
        val symbol = binding.symbolResult
        val rightAnswer = binding.resultNumber
        val wrongAnswer = binding.userNumber

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemList = answerList[position]

        holder.count.text = "${count++}."
        holder.firstNumber.text = itemList.firstNumber
        holder.secondNumber.text = itemList.secondNumber
        holder.symbol.text = itemList.symbol
        holder.rightAnswer.text = itemList.rightAnswer.toString()
        holder.wrongAnswer.text = itemList.wrongAnswer
    }

    override fun getItemCount(): Int {
        return answerList.size
    }
}