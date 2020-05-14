package com.yunusbedir.havadurumu.Data.Room

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yunusbedir.havadurumu.Model.User
import io.reactivex.Completable
import io.reactivex.Flowable


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */
interface UserDao {

    @Insert
    fun insert(user: User): Completable

    @Update
    fun update(user: User): Completable

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flowable<User>
}