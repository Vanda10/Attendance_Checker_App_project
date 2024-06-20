package kh.edu.rupp.fe.dse.attendencechecker.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.adapter.AttendanceAdapter
import kh.edu.rupp.fe.dse.attendencechecker.api.RetrofitClient
import kh.edu.rupp.fe.dse.attendencechecker.model.SessionInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ViewAttendanceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var attendanceAdapter: AttendanceAdapter
    private var sessionList: List<SessionInfo> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_attendence, container, false)

        recyclerView = view.findViewById(R.id.checked_in_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        attendanceAdapter = AttendanceAdapter(sessionList)
        recyclerView.adapter = attendanceAdapter

        val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)?.toIntOrNull()

        if (userId != null) {
            fetchSessionsAttended(userId)
        } else {
            Log.e("ViewAttendanceFragment", "User ID not found in SharedPreferences")
            // Handle case where user ID is not found in SharedPreferences
        }

        return view
    }

    private fun fetchSessionsAttended(userId: Int) {
        RetrofitClient.apiService.getSessionsAttended(userId).enqueue(object : Callback<List<SessionInfo>> {
            override fun onResponse(call: Call<List<SessionInfo>>, response: Response<List<SessionInfo>>) {
                if (response.isSuccessful) {
                    sessionList = response.body() ?: emptyList()
                    attendanceAdapter.setData(sessionList) // Update adapter data
                } else {
                    Log.e("ViewAttendanceFragment", "Response unsuccessful. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SessionInfo>>, t: Throwable) {
                Log.e("ViewAttendanceFragment", "Failed to fetch sessions attended", t)
            }
        })
    }
}
