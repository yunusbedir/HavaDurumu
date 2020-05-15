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
    var userId: Int = 1,
    @Embedded
    var region: Region,
    @ColumnInfo(name = "lang")
    var lang: String,
    @ColumnInfo(name = "units")
    var units: String
)