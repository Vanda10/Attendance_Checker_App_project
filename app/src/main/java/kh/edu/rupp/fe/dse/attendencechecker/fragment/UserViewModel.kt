package kh.edu.rupp.fe.dse.attendencechecker.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kh.edu.rupp.fe.dse.attendencechecker.model.UserResponse

class UserViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<UserResponse>()
    val userProfile: LiveData<UserResponse> get() = _userProfile

    fun setUserProfile(userResponse: UserResponse) {
        _userProfile.value = userResponse
    }
}
