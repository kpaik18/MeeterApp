package com.example.meeterapp.meeting.`object`

import MeetingDTO
import java.time.LocalDate

data class DayDTO(val date: LocalDate, var meetings: List<MeetingDTO>)
