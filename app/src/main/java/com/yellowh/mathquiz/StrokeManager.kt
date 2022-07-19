package com.yellowh.mathquiz

import android.util.Log
import android.view.MotionEvent
import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.*
import com.yellowh.mathquiz.function.rightAnswer
import com.yellowh.mathquiz.function.stringCheck
import com.yellowh.mathquiz.function.userNumber
import com.yellowh.mathquiz.function.wrongAnswer

object StrokeManager {
    private var inkBuilder = Ink.builder()
    private var strokeBuilder = Ink.Stroke.builder()
    private lateinit var model: DigitalInkRecognitionModel
    private var userInputNumber = ""

    fun addNewTouchEvent(event: MotionEvent) {
        val action = event.actionMasked
        val x = event.x
        val y = event.y
        val t = System.currentTimeMillis()

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> strokeBuilder.addPoint(
                Ink.Point.create(
                    x,
                    y,
                    t
                )
            )
            MotionEvent.ACTION_UP -> {
                strokeBuilder.addPoint(Ink.Point.create(x, y, t))
                inkBuilder.addStroke(strokeBuilder.build())
                strokeBuilder = Ink.Stroke.builder()
            }
            else -> {
                // Action not relevant for ink construction
            }
        }
    }

    fun download() {
        var modelIdentifier: DigitalInkRecognitionModelIdentifier? = null
        try {
            modelIdentifier =
                DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US")
        } catch (e: MlKitException) {
            // language tag failed to parse, handle error.
        }

        model =
            DigitalInkRecognitionModel.builder(modelIdentifier!!).build()

        val remoteModelManager = RemoteModelManager.getInstance()
        remoteModelManager.download(model, DownloadConditions.Builder().build())
            .addOnSuccessListener {
                Log.i("StrokeManager", "Model downloaded")
            }
            .addOnFailureListener { e: Exception ->
                Log.e("StrokeManager", "Error while downloading a model: $e")
            }
    }

    fun recognize(context: CalculationActivity) {

        val recognizer: DigitalInkRecognizer =
            DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model).build()
            )

        val ink = inkBuilder.build()


        recognizer.recognize(ink)
            .addOnSuccessListener { result: RecognitionResult ->


                // 필기 인식이 문자로 되는 경우 숫자로 변환
                userInputNumber = stringCheck(result.candidates[0].text)

                if (userNumber() == userInputNumber) {
                    context.showO(context)
                    rightAnswer()
                    context.nextView()
                    Log.d("로그", "정답 : ${userNumber()}, 사용자 입력 값 : $userInputNumber")
                } else {
                    context.showX(context)
                    wrongAnswer()
                    context.nextView()
                    context.insertDb()
                    Log.d("로그", "정답 => ${userNumber()} , 사용자 입력 값 => $userInputNumber")
                }

            }
            .addOnFailureListener { e: Exception ->
                Log.e("StrokeManager", "Error during recognition: $e")
            }

    }

    fun getUserNumber(): String {
        return userInputNumber
    }

    fun clear() {
        inkBuilder = Ink.builder()
    }
}