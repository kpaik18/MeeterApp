package com.example.meeterapp.meeting.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.meeterapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MeetingDetailsFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.meeting_details_view, container, false)

        val name = arguments?.getString("name")
        val status = arguments?.getString("status")
        val startTime = arguments?.getString("startTime")
        val endTime = arguments?.getString("endTime")
        view.findViewById<TextView>(R.id.meeting_details_view_title).text = name
        view.findViewById<TextView>(R.id.meeting_details_view_status).text = status
        view.findViewById<TextView>(R.id.meeting_details_view_start_time).text = startTime
        view.findViewById<TextView>(R.id.meeting_details_view_end_time).text = endTime
        println()

        return view
    }
}