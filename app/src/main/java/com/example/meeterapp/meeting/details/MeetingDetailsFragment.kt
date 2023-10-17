package com.example.meeterapp.meeting.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meeterapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MeetingDetailsFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.meeting_details_view, container, false)

        // Retrieve meeting details and set them in the UI elements

        return view
    }
}