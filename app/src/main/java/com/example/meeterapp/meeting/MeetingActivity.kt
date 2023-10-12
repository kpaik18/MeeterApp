package com.example.meeterapp.meeting

import TokenManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.meeterapp.UnsafeHttpClient
import com.example.meeterapp.meeting.`object`.DayDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.String
import java.time.LocalDate
import java.util.concurrent.CountDownLatch


class MeetingActivity : ComponentActivity() {
    private lateinit var meetingService: MeetingService
    val retrofit = Retrofit.Builder()
        .baseUrl("https://meeterbacktest.onrender.com/") // Your base URL goes here
        .addConverterFactory(GsonConverterFactory.create())
        .client(UnsafeHttpClient.getUnsafeOkHttpClient()?.build())
        .build()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.meeterapp.R.layout.activity_meeting)
        meetingService = retrofit.create(MeetingService::class.java)
        val containerLayout = findViewById<LinearLayout>(com.example.meeterapp.R.id.containerLayout)

//        var day = getDayDTO()

        for (i in 0..23) {
            val itemView: View =
                LayoutInflater.from(this).inflate(com.example.meeterapp.R.layout.hour_view, null)
            val hourTextView: TextView =
                itemView.findViewById(com.example.meeterapp.R.id.hourTextView)
            hourTextView.text = String.format("%02d:00", i)
            containerLayout.addView(itemView)
        }


        var localDay = LocalDate.now()

        meetingService.getDayMeetings("Bearer " + TokenManager.accessToken!!, localDay)
            .enqueue(object : Callback<DayDTO> {
                override fun onResponse(call: Call<DayDTO>, response: Response<DayDTO>) {
                    if (response.isSuccessful) {
                        println()
                    } else {
                        println()
                    }

                }

                override fun onFailure(call: Call<DayDTO>, t: Throwable) {
                    println()
                }

            })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayDTO(): DayDTO? {
        val latch = CountDownLatch(1)
        var result: DayDTO? = null
        var localDay = LocalDate.now()
        meetingService.getDayMeetings(TokenManager.accessToken!!, localDay)
            .enqueue(object : Callback<DayDTO> {
                override fun onResponse(call: Call<DayDTO>, response: Response<DayDTO>) {
                    if (response.isSuccessful) {
                        println()
                    }
                    latch.countDown()

                }

                override fun onFailure(call: Call<DayDTO>, t: Throwable) {
                    println()
                    latch.countDown()

                }

            })
        latch.await()
        return result
    }

    private fun showToast(message: kotlin.String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}