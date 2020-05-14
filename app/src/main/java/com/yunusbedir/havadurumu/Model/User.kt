package com.yunusbedir.havadurumu.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val userId: Int = 1,
    @ColumnInfo(name = "lat")
    val lat: Int,
    @ColumnInfo(name = "lon")
    val lon: Int,
    @ColumnInfo(name = "lang")
    val lang: String,
    @ColumnInfo(name = "units")
    val units: String
)