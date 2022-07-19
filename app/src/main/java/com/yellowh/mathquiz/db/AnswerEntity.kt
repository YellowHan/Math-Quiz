package com.yellowh.mathquiz.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "firstNumber") val firstNumber: String,
    @ColumnInfo(name = "secondNumber") val secondNumber: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "rightAnswer") val rightAnswer: String,
    @ColumnInfo(name = "wrongAnswer") val wrongAnswer: String
)