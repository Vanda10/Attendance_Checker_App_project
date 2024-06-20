package kh.edu.rupp.fe.dse.attendencechecker.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kh.edu.rupp.fe.dse.attendencechecker.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kh.edu.rupp.fe.dse.attendencechecker.Result

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, password: String) = liveData(context = Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val user = repository.login(email, password)
            emit(Result.Success(user))
        } catch (exception: Exception) {
            emit(Result.Error(exception))
        }
    }
}
class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

