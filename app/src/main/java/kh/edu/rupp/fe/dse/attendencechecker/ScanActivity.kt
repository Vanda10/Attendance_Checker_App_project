package kh.edu.rupp.fe.dse.attendencechecker//package kh.edu.rupp.fe.dse.attendencechecker
//
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.inputmethod.InputBinding
//import android.widget.Toast
//import androidx.activity.result.ActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.core.content.ContextCompat
//import com.google.zxing.integration.android.IntentIntegrator
//import com.google.zxing.integration.android.IntentResult
//import com.journeyapps.barcodescanner.ScanContract
//import com.journeyapps.barcodescanner.ScanIntentResult
//import com.journeyapps.barcodescanner.ScanOptions
//import kh.edu.rupp.fe.dse.attendencechecker.databinding.ActivityScanBinding
//
//class ScanActivity : AppCompatActivity() {
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//            isGranted: Boolean -> if (isGranted) {
//                showCamera()
//
//            }
//            else{
//                //Explain
//            }
//        }
//
//    private val scanLauncher =
//        registerForActivityResult(ScanContract()) {result: ScanIntentResult ->
//            run {
//                if (result.contents ==  null) {
//                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
//                }
//                else {
//                    setResult(result.contents)
//                }
//            }
//
//        }
//
////    override fun setResult(string: Strings) {
////        binding.textResult.text = String
////    }
//
//    private lateinit var binding: ActivityScanBinding
//    private fun showCamera() {
//        val options = ScanOptions()
//        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//        options.setPrompt("Scan QR code")
//        options.setCameraId(0)
//        options.setBeepEnabled(false)
//        options.setBarcodeImageEnabled(true)
//        options.setOrientationLocked(false)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initBinding()
//        initViews()
//    }
//
//    private fun initViews() {
//        binding.fab.setOnClickListener {
//            checkPermissionCamera(this)
//        }
//    }
//
//    private fun checkPermissionCamera(context: Context) {
//        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//
//        }
//        else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
//            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT)
//        }
//        else {
//            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
//        }
//    }
//
//    private fun initBinding() {
//        binding = ActivityScanBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//    }
//}
