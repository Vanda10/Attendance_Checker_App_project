// HomeFragment.kt
package kh.edu.rupp.fe.dse.attendencechecker.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.adapter.ClassListAdapter
import kh.edu.rupp.fe.dse.attendencechecker.repository.ClassRepository
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var classRepository: ClassRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classRepository = ClassRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.checked_in_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ClassListAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchClassList(adapter)

        val viewAttendanceCard = view.findViewById<CardView>(R.id.viewAttendanceCard)
        viewAttendanceCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAttendanceFragment)
        }

        // Set current date
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val currentDate = SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = currentDate
    }

    private fun fetchClassList(adapter: ClassListAdapter) {
        classRepository.getSessionList { sessionList ->
            sessionList?.let {
                Log.d(TAG, "Session list retrieved successfully: $it")
                adapter.updateSessionList(it)
            } ?: run {
                Log.e(TAG, "Session list is null")
            }
        }
    }
}
