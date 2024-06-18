package kh.edu.rupp.fe.dse.attendencechecker.api

import kh.edu.rupp.fe.dse.attendencechecker.model.AttendanceRequest
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("session_name/")
    fun getSessionList(): Call<List<ClassUiModel>>

    @POST("api/attendance/")
    fun createAttendance(@Body attendanceRequest: AttendanceRequest): Call<Void>
}




