package kh.edu.rupp.fe.dse.attendencechecker.api

import kh.edu.rupp.fe.dse.attendencechecker.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("api/sessions/by_class_code/{classcode}")
    fun getClassesByClassCode(@Path("classcode") classcode: String): Call<List<ClassUiModel>>

    @POST("api/api/attendance/")
    fun createAttendance(@Body attendanceRequest: AttendanceRequest): Call<Void>

    @FormUrlEncoded
    @POST("token/")
    fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): Call<User>

    @GET("me/")
    fun getUserProfile(): Call<UserResponse>

    @GET("api/sessions/attended/{user_id}")
    fun getSessionsAttended(@Path("user_id") userId: Int): Call<List<SessionInfo>>

    companion object {
        private const val BASE_URL = "https://attendence-checker-91c537c9caf5.herokuapp.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
