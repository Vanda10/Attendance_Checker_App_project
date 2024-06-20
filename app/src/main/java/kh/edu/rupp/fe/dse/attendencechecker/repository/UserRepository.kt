package kh.edu.rupp.fe.dse.attendencechecker.repository

import kh.edu.rupp.fe.dse.attendencechecker.api.ApiService
import kh.edu.rupp.fe.dse.attendencechecker.model.User
import retrofit2.Call
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): User {
        val response: Response<User> = apiService.login(email, password).execute()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }
}
