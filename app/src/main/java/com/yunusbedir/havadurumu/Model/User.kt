package com.yunusbedir.havadurumu.Model

import androidx.room.ColumnInfo
import androidx.room.Embedded
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
    @Embedded
    val region: Region,
    @ColumnInfo(name = "lang")
    val lang: String,
    @ColumnInfo(name = "units")
    val units: String
)