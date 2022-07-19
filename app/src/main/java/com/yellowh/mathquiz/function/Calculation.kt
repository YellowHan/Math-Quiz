package com.yellowh.mathquiz.function

var firstNumber = ""
var secondNumber = ""
var symbol = ""
var result = ""

fun calculation(firstNum: Int, secondNum: Int, symbol: String) {

    when (symbol) {
        "+" -> {
            result = (firstNum + secondNum).toString()
        }
        "−" -> {
            result = (firstNum - secondNum).toString()
        }
        "×" -> {
            result = (firstNum * secondNum).toString()
        }
        "÷" -> {
            result = if (firstNum % secondNum > 0) {
                (firstNum / secondNum).toString() + "..." + (firstNum % secondNum).toString()
            } else {
                (firstNum / secondNum).toString()
            }
        }
    }

    inputAnswer(firstNum.toString(), secondNum.toString(), symbol, result)

}

fun userNumber(): String {
    return result
}

fun inputAnswer(number1: String, number2: String, _symbol: String, rightNumber: String) {
    firstNumber = number1
    secondNumber = number2
    symbol = _symbol
    result = rightNumber
}


