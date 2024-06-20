// LoginActivity.kt
package kh.edu.rupp.fe.dse.attendencechecker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kh.edu.rupp.fe.dse.attendencechecker.api.RetrofitClient
import kh.edu.rupp.fe.dse.attendencechecker.fragment.LoginViewModel
import kh.edu.rupp.fe.dse.attendencechecker.fragment.LoginViewModelFactory
import kh.edu.rupp.fe.dse.attendencechecker.model.User
import kh.edu.rupp.fe.dse.attendencechecker.model.UserResponse
import kh.edu.rupp.fe.dse.attendencechecker.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(UserRepository(RetrofitClient.apiService))
    }

    private lateinit var loginButton: Button
    private lateinit var studentEmail: EditText
    private lateinit var studentPassword: EditText
    private lateinit var loadingSpinner: ProgressBar // Added ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user is already logged in
        if (isLoggedIn()) {
            navigateToMainActivity()
            return
        }

        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button)
        studentEmail = findViewById(R.id.student_email)
        studentPassword = findViewById(R.id.student_password)
        loadingSpinner = findViewById(R.id.loading_spinner) // Initialize loading spinner

        loginButton.setOnClickListener {
            val email = studentEmail.text.toString().trim()
            val password = studentPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show loading spinner
            loadingSpinner.visibility = View.VISIBLE

            loginViewModel.login(email, password).observe(this, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        // No need to handle loading here as we are already showing spinner
                    }

                    is Result.Success<*> -> {
                        // Hide loading spinner on success
                        loadingSpinner.visibility = View.GONE

                        val user = result.data as? User
                        val token = user?.accessToken
                        if (token != null) {
                            Log.d("AccessToken", "Token: $token")
                            saveToken(token)
                            RetrofitClient.setToken(token)

                            // Fetch user profile after saving token
                            fetchUserProfile {
                                navigateToMainActivity()
                                finish() // Close LoginActivity
                            }
                        } else {
                            Toast.makeText(this, "Login Failed: Missing token", Toast.LENGTH_SHORT).show()
                        }

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        // Hide loading spinner on error
                        loadingSpinner.visibility = View.GONE

                        Toast.makeText(this, "Login Failed: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        sharedPreferences.edit().putString("token", token).apply()
    }

    private fun fetchUserProfile(onComplete: () -> Unit) {
        RetrofitClient.apiService.getUserProfile().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { userProfile ->
                        Log.d("ProfileFetch", "User Profile: $userProfile")
                        saveUserProfile(userProfile)
                    } ?: Log.e("ProfileFetch", "User profile is null")
                } else {
                    Log.e("ProfileFetch", "Failed to load profile: ${response.errorBody()?.string()}")
                    Toast.makeText(this@LoginActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
                onComplete()
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("ProfileFetch", "Failed to load profile", t)
                Toast.makeText(this@LoginActivity, "Failed to load profile: ${t.message}", Toast.LENGTH_SHORT).show()
                onComplete()
            }
        })
    }

    private fun saveUserProfile(userProfile: UserResponse) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("user_id", userProfile.user_id.toString())
            putString("user_name", userProfile.user_name)
            putString("email", userProfile.email)
            putString("dob", userProfile.dob)
            putString("Department", userProfile.department)
            putString("classcode", userProfile.class_code)
            apply()
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        return !token.isNullOrBlank()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close LoginActivity
    }
}
