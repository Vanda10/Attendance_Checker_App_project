package kh.edu.rupp.fe.dse.attendencechecker.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import com.squareup.picasso.Picasso
import kh.edu.rupp.fe.dse.attendencechecker.R
import kh.edu.rupp.fe.dse.attendencechecker.api.RetrofitClient
import kh.edu.rupp.fe.dse.attendencechecker.model.AttendanceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanFragment : Fragment() {
    private lateinit var barcodeView: BarcodeView
    private lateinit var attendanceStatus: TextView
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var qrCodeImage: ImageView
    private val CAMERA_PERMISSION_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        barcodeView = view.findViewById(R.id.barcode_view)
        attendanceStatus = view.findViewById(R.id.attendance_status)
        loadingSpinner = view.findViewById(R.id.loading_spinner)
        qrCodeImage = view.findViewById(R.id.qr_code_image)

        // Check for camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            startScanning()
        }

        return view
    }

    private fun startScanning() {
        barcodeView.decodeContinuous(callback)
    }

    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            println("Scanned: ${result.text}")
            Toast.makeText(requireContext(), "QR code scanned", Toast.LENGTH_SHORT).show()

            barcodeView.pause()
            loadingSpinner.visibility = View.VISIBLE

            val sessionUrl = result.text.replace("127.0.0.1", "10.0.2.2")
            val sessionId = extractSessionIdFromUrl(sessionUrl)
            if (sessionId != null) {
                qrCodeImage.visibility = View.VISIBLE
                Picasso.get().load(result.text).into(qrCodeImage)

                val userId = getUserIdFromSharedPreferences()
                val userIdInt = userId?.toIntOrNull()

                if (userIdInt != null) {
                    sendAttendanceData(userIdInt, sessionId)
                } else {
                    loadingSpinner.visibility = View.GONE
                    attendanceStatus.text = "Invalid user ID"
                    attendanceStatus.visibility = View.VISIBLE
                }
            } else {
                loadingSpinner.visibility = View.GONE
                attendanceStatus.text = "Invalid session ID"
                attendanceStatus.visibility = View.VISIBLE
            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    private fun getUserIdFromSharedPreferences(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_id", null)
    }

    private fun sendAttendanceData(userId: Int, sessionId: Int) {
        val attendanceRequest = AttendanceRequest(user_id = userId, session_id = sessionId)
        val call = RetrofitClient.apiService.createAttendance(attendanceRequest)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                loadingSpinner.visibility = View.GONE
                when {
                    response.isSuccessful -> {
                        println("Attendance recorded successfully")
                        attendanceStatus.text = "Attendance recorded successfully"
                    }
                    response.code() == 400 -> {
                        println("Attendance already recorded for this session and user.")
                        attendanceStatus.text = "Attendance already recorded for this session and user."
                    }
                    else -> {
                        println("Failed to record attendance")
                        attendanceStatus.text = "Failed to record attendance"
                        val errorBody = response.errorBody()?.string()
                        println("Error: $errorBody")
                    }
                }
                attendanceStatus.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                loadingSpinner.visibility = View.GONE
                println("Error: ${t.message}")
                attendanceStatus.text = "Error: ${t.message}"
                attendanceStatus.visibility = View.VISIBLE
            }
        })
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun extractSessionIdFromUrl(url: String): Int? {
            return url.split("/").lastOrNull()?.toIntOrNull()
        }
    }
}
