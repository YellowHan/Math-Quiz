package com.yellowh.mathquiz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.yellowh.mathquiz.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            StrokeManager.download()
        }.start()

        val slide_up: Animation = AnimationUtils.loadAnimation(
            this,
            R.anim.slide_up
        )

        binding.splashTitle.startAnimation(slide_up)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                binding.animationView.visibility = View.VISIBLE
            }, 1000
        )

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3500
        )



    }
}