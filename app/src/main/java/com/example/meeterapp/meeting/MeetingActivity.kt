package com.example.meeterapp.meeting

import MeetingDTO
import TokenManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.meeterapp.R
import com.example.meeterapp.meeting.`object`.DayDTO
import com.example.meeterapp.retrofit.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String
import java.time.LocalDate
import java.util.concurrent.CountDownLatch


class MeetingActivity : ComponentActivity() {
    private lateinit var meetingService: MeetingService

    @RequiresApi(Build.VERSION_CODES.O)
    val retrofit = RetrofitFactory.getRetrofitClient()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.meeterapp.R.layout.activity_meeting)
        meetingService = retrofit.create(MeetingService::class.java)
        val containerLayout = findViewById<LinearLayout>(com.example.meeterapp.R.id.containerLayout)


        for (i in 0..23) {
            val itemView: View =
                LayoutInflater.from(this).inflate(com.example.meeterapp.R.layout.hour_view, null)
            val hourTextView: TextView =
                itemView.findViewById(com.example.meeterapp.R.id.hourTextView)
            hourTextView.text = String.format("%02d:00", i)
            containerLayout.addView(itemView)
        }

        containerLayout.post {
            val childView = containerLayout.getChildAt(9)
            if (childView != null) {
                val y = childView.top // Get the top position of the child view
                val scrollView =
                    findViewById<ScrollView>(R.id.scrollView) // Replace with your ScrollView ID
                scrollView.smoothScrollTo(0, y) // Scroll to the specified position
            }
        }

        setMeetingsOnView()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMeetingsOnView() {
        var localDay = LocalDate.now()
        var day: DayDTO? = null
        var latch = CountDownLatch(1)

        meetingService.getDayMeetings("Bearer " + TokenManager.accessToken!!, localDay)
            .enqueue(object : Callback<DayDTO> {
                override fun onResponse(call: Call<DayDTO>, response: Response<DayDTO>) {
                    if (response.isSuccessful) {
                        day = response.body()
                        var meetings: List<MeetingDTO>? = day?.meetings
                        if (meetings != null) {
                            for (meeting in meetings) {
                                var startHour = meeting.start.hour
                                val containerLayout =
                                    findViewById<LinearLayout>(com.example.meeterapp.R.id.containerLayout)
                                val hourChild = containerLayout.getChildAt(startHour)
                                var meetingContainerView =
                                    hourChild.findViewById<View>(R.id.meeting_container)
                                var meetingTextView =
                                    hourChild.findViewById<TextView>(R.id.meeting_text)
                                meetingTextView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        this@MeetingActivity,
                                        R.color.meeting_available_color
                                    )
                                )
                                meetingTextView.text = meeting.name
                            }
                        }
                        println()
                    } else {
                        showToast("Response is not successful")
                    }
                }

                override fun onFailure(call: Call<DayDTO>, t: Throwable) {
                    showToast("You're such a Failure")
                }

            })

    }


    private fun showToast(message: kotlin.String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}