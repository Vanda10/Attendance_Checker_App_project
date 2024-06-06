import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.adapter.ClassListAdapter
import kh.edu.rupp.fe.dse.attendencechecker.model.ClassUiModel
import kh.edu.rupp.fe.dse.attendencechecker.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    // Define the parameter arguments as constants
    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.checked_in_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchClassList()

        val viewAttendanceCard = view.findViewById<CardView>(R.id.viewAttendanceCard)
        viewAttendanceCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAttendanceFragment)
        }
    }

    private fun fetchClassList() {
        val call = RetrofitClient.apiService.getClassList()
        call.enqueue(object : Callback<List<ClassUiModel>> {
            override fun onResponse(
                call: Call<List<ClassUiModel>>,
                response: Response<List<ClassUiModel>>
            ) {
                if (response.isSuccessful) {
                    val classList = response.body()
                    classList?.let {
                        updateUIWithClassList(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<ClassUiModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun updateUIWithClassList(classList: List<ClassUiModel>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.checked_in_recycler_view)
        recyclerView?.adapter = ClassListAdapter(classList)
    }
}
