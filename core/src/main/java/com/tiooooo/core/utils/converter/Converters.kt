package com.tiooooo.core.utils.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tiooooo.core.domain.model.Genre

class Converters {
//    @TypeConverter
//    fun listToJson(value: List<Genre>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) =
//        Gson().fromJson(value, Array<Genre>::class.java).toList()

    private val gson = Gson()

    @TypeConverter
    fun arrayListToJson(list: List<Genre>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun jsonToArrayList(jsonData: String?): List<Genre>? {
        return if (jsonData == null) null else gson.fromJson(
            jsonData,
            object : TypeToken<List<Genre>?>() {}.type
        )
    }
}