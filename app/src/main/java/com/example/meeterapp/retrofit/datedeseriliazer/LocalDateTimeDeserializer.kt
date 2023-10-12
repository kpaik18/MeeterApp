package com.example.meeterapp.retrofit.datedeseriliazer

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        val dateTimeAsString = json?.asString
        if (dateTimeAsString != null) {
            try {
                val formatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                return LocalDateTime.parse(dateTimeAsString, formatter)
            } catch (e: Exception) {
                throw JsonParseException(e)
            }
        }
        throw JsonParseException("Date-time is null or not a string")
    }
}