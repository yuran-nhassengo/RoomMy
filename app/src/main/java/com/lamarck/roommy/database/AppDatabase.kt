package com.lamarck.roommy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lamarck.roommy.database.dao.UserDao
import com.lamarck.roommy.database.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{

        private const val DATABASE_NAME: String = "nome-do-banco-de-dados"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE?: synchronized(this){
                INSTANCE?: buildDatabase(context).also{ INSTANCE =it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            )
                .build()
    }



}