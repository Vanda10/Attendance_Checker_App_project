package kh.edu.rupp.fe.dse.attendencechecker.fragment
import android.content.Context
import android.os.Bundle
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
    private lateinit var noUpcomingSessionTextView: TextView

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

        noUpcomingSessionTextView = view.findViewById(R.id.no_upcoming_session_text_view)

        val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val classcode = sharedPreferences.getString("classcode", null)

        if (classcode != null) {
            fetchClassList(classcode, adapter)
        } else {
            displayNoUpcomingSessionMessage()
        }

        val viewAttendanceCard = view.findViewById<CardView>(R.id.viewAttendanceCard)
        viewAttendanceCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAttendanceFragment)
        }

        // Set current date
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = currentDate

        // Set current time in Bangkok timezone
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val timeZone = TimeZone.getTimeZone("Asia/Bangkok")
        sdf.timeZone = timeZone
        val currentTime = sdf.format(Date())
        timeTextView.text = currentTime
    }

    private fun fetchClassList(classcode: String, adapter: ClassListAdapter) {
        classRepository.getClassesByClassCode(classcode) { classList ->
            classList?.let {
                adapter.updateClassList(it)
                if (it.isEmpty()) {
                    displayNoUpcomingSessionMessage()
                } else {
                    noUpcomingSessionTextView.visibility = View.GONE
                }
            } ?: run {
                displayNoUpcomingSessionMessage()
            }
        }
    }

    private fun displayNoUpcomingSessionMessage() {
        noUpcomingSessionTextView.visibility = View.VISIBLE
    }
}
