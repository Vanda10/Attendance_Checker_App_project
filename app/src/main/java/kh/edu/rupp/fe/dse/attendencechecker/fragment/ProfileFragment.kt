package kh.edu.rupp.fe.dse.attendencechecker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kh.edu.rupp.fe.dse.attendencechecker.R

class ProfileFragment : Fragment() {

    private lateinit var profileName: EditText
    private lateinit var profileDob: EditText
    private lateinit var editProfileButton: Button
    private lateinit var logOutButton: Button

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileName = view.findViewById(R.id.profile_name)
        profileDob = view.findViewById(R.id.profile_dob)
        editProfileButton = view.findViewById(R.id.btn_edit_profile)
        logOutButton = view.findViewById(R.id.btn_log_out)

        editProfileButton.setOnClickListener {
            toggleEditing()
        }

        logOutButton.setOnClickListener {
            showLogOutConfirmationDialog()
        }

        return view
    }

    private fun toggleEditing() {
        val isEditing = profileName.isEnabled

        profileName.isEnabled = !isEditing
        profileDob.isEnabled = !isEditing

        if (isEditing) {
            // Save changes
            val newName = profileName.text.toString()
            val newDob = profileDob.text.toString()

            // Ideally, save these values to your data source
            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
            editProfileButton.text = "Edit Profile"
        } else {
            // Enable editing
            Toast.makeText(context, "You can now edit your profile", Toast.LENGTH_SHORT).show()
            editProfileButton.text = "Save Changes"
        }
    }

    private fun showLogOutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            // Implement log out functionality here
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}
