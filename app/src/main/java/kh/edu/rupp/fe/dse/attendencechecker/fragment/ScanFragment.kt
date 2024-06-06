package kh.edu.rupp.fe.dse.attendencechecker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import kh.edu.rupp.fe.dse.attendencechecker.R

class ScanFragment : Fragment() {
    private lateinit var barcodeView: BarcodeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        barcodeView = view.findViewById(R.id.barcode_view)
        barcodeView.decodeContinuous(callback)
        return view
    }

    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // Handle the scanned result here
            // For example, display the scanned result in a Toast
            // Toast.makeText(context, "Scanned: ${result.text}", Toast.LENGTH_LONG).show()
            println("Scanned: ${result.text}")
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
            // Handle possible result points if needed
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
     }
}
