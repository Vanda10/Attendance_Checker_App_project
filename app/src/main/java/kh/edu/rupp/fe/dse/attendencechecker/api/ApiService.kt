package kh.edu.rupp.fe.dse.attendencechecker.api

import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("classes/")
    fun getClassList(): Call<List<ClassUiModel>>
}

