package com.yellowh.mathquiz.db

import androidx.room.*

@Dao
interface AnswerDao {

    @Query("SELECT *  FROM AnswerEntity")
    fun getAll() : List<AnswerEntity>

    @Query("DELETE FROM AnswerEntity")
    fun allDelete() : Int

    @Insert
    fun insertAnswer(answer: AnswerEntity)

    @Delete
    fun deleteAnswer(answer: AnswerEntity)

}