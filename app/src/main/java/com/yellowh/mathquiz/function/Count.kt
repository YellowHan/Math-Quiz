package com.yellowh.mathquiz.function

var currentCount = 1
var rightCount = 0
var wrongCount = 0

var date = 0

fun userCount() {
    if(currentCount < 11) {
        currentCount++
    }
}

fun countReset() {
    currentCount = 1
    rightCount = 0
    wrongCount = 0
}


fun rightAnswer() {
    rightCount++
}

fun wrongAnswer() {
    wrongCount++
}

fun getCount(): Int {
    return currentCount
}

fun getOCount(): Int {
    return rightCount
}

fun getXCount(): Int {
    return wrongCount
}