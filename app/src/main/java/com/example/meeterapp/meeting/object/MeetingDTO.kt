import com.example.meeterapp.meeting.`object`.MeetingStatus
import com.example.meeterapp.meeting.`object`.UserDTO
import java.time.LocalDateTime


data class MeetingDTO(
    val id: Long,
    val owner: UserDTO,
    val participant: UserDTO,
    val name: String,
    val status: MeetingStatus,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val repeaterId: Long
) {

}