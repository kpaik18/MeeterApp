package com.example.meeterapp.retrofit.datedeseriliazer

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        val dateAsString = json?.asString
        if (dateAsString != null) {
            try {
                // Define the format of your date string
                val formatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy") // Adjust the pattern to match your API's date format
                return LocalDate.parse(dateAsString, formatter)
            } catch (e: Exception) {
                throw JsonParseException(e)
            }
        }
        throw JsonParseException("Date is null or not a string")
    }
}