// ClassRepository.kt
package kh.edu.rupp.fe.dse.attendencechecker.repository

import android.util.Log
import kh.edu.rupp.fe.dse.attendencechecker.api.RetrofitClient
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassRepository {
    fun getSessionList(callback: (List<ClassUiModel>?) -> Unit) {
        val call = RetrofitClient.apiService.getSessionList()
        call.enqueue(object : Callback<List<ClassUiModel>> {
            override fun onResponse(call: Call<List<ClassUiModel>>, response: Response<List<ClassUiModel>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ClassRepository", "Failed to get session list: ${response.errorBody()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<ClassUiModel>>, t: Throwable) {
                Log.e("ClassRepository", "Error fetching session list", t)
                callback(null)
            }
        })
    }
}
