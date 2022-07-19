package com.yellowh.mathquiz.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yellowh.mathquiz.databinding.ActivityDivisionBinding
import com.yellowh.mathquiz.function.firstNumber
import com.yellowh.mathquiz.function.secondNumber
import com.yellowh.mathquiz.function.symbol

class Fragment_div : Fragment() {

    lateinit var binding: ActivityDivisionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityDivisionBinding.inflate(layoutInflater)

        binding.firstNumber.text = firstNumber
        binding.secondNumber.text = secondNumber
        binding.symbol?.text = symbol

        return binding.root
    }
}