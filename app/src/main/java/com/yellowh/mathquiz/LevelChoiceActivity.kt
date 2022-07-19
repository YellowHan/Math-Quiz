package com.yellowh.mathquiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yellowh.mathquiz.databinding.ActivityLevelChoiceBinding

class LevelChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLevelChoiceBinding
    private var _symol = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLevelChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("symbol")) {
            _symol = intent.getStringExtra("symbol").toString()
        }

        binding.level1.setOnClickListener {
            nextPage(_symol, 1)
        }

        binding.level2.setOnClickListener {
            nextPage(_symol, 2)
        }

        binding.level3.setOnClickListener {
            nextPage(_symol, 3)
        }

        binding.level4.setOnClickListener {
            nextPage(_symol, 4)
        }

    }

    private fun nextPage(symbol: String, level: Int) {

        val intent = Intent(this, CalculationActivity::class.java)
        intent.putExtra("symbol", symbol)
        intent.putExtra("level", level)
        startActivity(intent)
        finish()
    }
}