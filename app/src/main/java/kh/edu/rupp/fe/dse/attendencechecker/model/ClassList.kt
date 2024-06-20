package kh.edu.rupp.fe.dse.attendencechecker.model
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

interface ClassList

data class ClassUiModel(
    val session_name: String,
    val session_date: String,
    val session_start_time: String
)

data class AttendanceRequest(
    val user_id: Int,
    val session_id: Int,
    val timestamp: String = getCurrentTimestamp() // Include the timestamp if needed
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("access_token")
    val accessToken: String,

)

data class UserResponse(
    val user_id: Int,
    val email: String,
    val user_name: String,
    val dob: String,
    val department: String,
    val class_code: String
)

data class SessionInfo(
    val session_date: String,
    val session_start_time: String,
    val session_end_time: String,
    val session_name: String,
    val class_code: String,
    val session_id: String
)



// Function to get the current timestamp in ISO 8601 format
fun getCurrentTimestamp(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(Date())
}