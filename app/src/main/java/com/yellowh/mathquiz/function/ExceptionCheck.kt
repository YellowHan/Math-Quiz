package com.yellowh.mathquiz.function

import android.util.Log

fun stringCheck(userNumber: String): String {
    val result = when (userNumber) {
        "l", "\\", "I" -> "1"
        "ll", "\\\\", "II" -> "11"
        "z", "Z" -> "2"
        "n" -> "7"
        "o" -> "0"
        "lo" -> "10"
        "in" -> "17"
        "zo" -> "20"
        "nn" -> "77"
        "1/2" -> "112"
        "So" -> "50"
        "l...l", "I...I" -> "1...1"
        else -> when (userNumber[userNumber.length - 1].toString()) {
            "l", "I", "|" -> "${userNumber.substring(0 until userNumber.length - 1)}1"
            else -> {
                userNumber
            }
        }
    }
    Log.d("로그", result)
    return result
}