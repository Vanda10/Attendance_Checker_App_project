package kh.edu.rupp.fe.dse.attendencechecker.repository

import kh.edu.rupp.fe.dse.attendencechecker.api.RetrofitClient
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassRepository {

    fun getClassList(callback: (List<ClassUiModel>?) -> Unit) {
        val call = RetrofitClient.apiService.getClassList()
        call.enqueue(object : Callback<List<ClassUiModel>> {
            override fun onResponse(call: Call<List<ClassUiModel>>, response: Response<List<ClassUiModel>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<ClassUiModel>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
