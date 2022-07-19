package com.yellowh.mathquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
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

    var mInterstitialAd: InterstitialAd? = null

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
            setNextPage()
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

    private fun setInterstitialAds() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
//            "ca-app-pub-3940256099942544/1033173712", // 테스트용
            "ca-app-pub-7122564754479309~5013301064",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("ads log", "전면 광고가 로드 실패했습니다. ${adError.responseInfo}")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("ads log", "전면 광고가 로드되었습니다.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun setNextPage() {
        binding.btnNextPage.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d("ads log", "전면 광고가 닫혔습니다.")

                        val intent = Intent(this@ResultActivity, WrongAnswerActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        Log.d("ads log", "전면 광고가 열리는 데 실패했습니다.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d("ads log", "전면 광고가 성공적으로 열렸습니다.")
                        mInterstitialAd = null
                    }
                }

                mInterstitialAd!!.show(this)
            } else {
                Log.d("InterstitialAd", "전면 광고가 로딩되지 않았습니다.")
                Toast.makeText(
                    this@ResultActivity,
                    "잠시 후 다시 시도해주세요.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setInterstitialAds()
    }
}