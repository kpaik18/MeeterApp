package com.example.meeterapp.meeting

import com.example.meeterapp.meeting.`object`.DayDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.time.LocalDate

interface MeetingService {
    @GET("api/meeting/day")
    fun getDayMeetings(
        @Header("Authorization") token: String,
        @Query("date") date: LocalDate
    ): Call<DayDTO>
}