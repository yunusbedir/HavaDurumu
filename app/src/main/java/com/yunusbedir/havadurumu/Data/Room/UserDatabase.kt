package com.yunusbedir.havadurumu.Data.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yunusbedir.havadurumu.Model.User


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */
@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        //Volatile -> farklı threadlerde de aynı değişken çalışır.
        @Volatile
        private var instance: UserDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: makeDatabase(context).also {
                instance = it
            }

        }

        private fun makeDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "country_database"
            ).build()
    }
}