package com.tiooooo.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "casts")
data class CastsEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "film_id") var filmId: String,
    @ColumnInfo(name = "name") var name: String ? = "",
    @ColumnInfo(name = "profile_path") var profilePath: String? = "",
    @ColumnInfo(name = "cast_id") var castId: Int,
    @ColumnInfo(name = "gender") var gender: Int? = 0

)
