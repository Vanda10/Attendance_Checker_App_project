package kh.edu.rupp.fe.dse.attendencechecker.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kh.edu.rupp.fe.dse.attendencechecker.LoginActivity
import kh.edu.rupp.fe.dse.attendencechecker.R
class ProfileFragment : Fragment() {

    private lateinit var profileName: EditText
    private lateinit var profileDob: EditText
    private lateinit var logOutButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var departmentTextView: TextView
    private lateinit var classCodeTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileName = view.findViewById(R.id.profile_name)
        profileDob = view.findViewById(R.id.profile_dob)
        logOutButton = view.findViewById(R.id.btn_log_out)
        departmentTextView = view.findViewById(R.id.student_department)
        classCodeTextView = view.findViewById(R.id.class_code)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        logOutButton.setOnClickListener {
            showLogOutConfirmationDialog()
        }

        fetchUserProfileFromPreferences()

        return view
    }

    private fun fetchUserProfileFromPreferences() {
        val userName = sharedPreferences.getString("user_name", "")
        val dob = sharedPreferences.getString("dob", "")
        val department = sharedPreferences.getString("Department", "")
        val classCode = sharedPreferences.getString("classcode", "")

        updateUI(userName, dob, department, classCode)
    }

    private fun updateUI(userName: String?, dob: String?, department: String?, classCode: String?) {
        profileName.setText(userName)
        profileDob.setText(dob)
        departmentTextView.text = department
        classCodeTextView.text = classCode
    }

    private fun showLogOutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            logOut()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun logOut() {
        // Clear relevant SharedPreferences entries on logout
        sharedPreferences.edit().clear().apply()

        // Navigate to login screen
        navigateToLogin()

        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
