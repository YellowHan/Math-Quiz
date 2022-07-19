package com.yellowh.mathquiz.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnswerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAnswerDao() : AnswerDao

    companion object {
        private const val databaseName = "db_answer"
        private var appDatabase : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if(appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
            }
            return appDatabase
        }
    }
}
