package kh.edu.rupp.fe.dse.attendencechecker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.zxing.integration.android.IntentIntegrator
import kh.edu.rupp.fe.dse.attendencechecker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Find the "Start" card view
        val startCardView: CardView = findViewById(R.id.startCardView)

        // Set OnClickListener for the "Start" card view
        startCardView.setOnClickListener {
            // Start the ScanActivity
            startActivity(Intent(this@MainActivity, QRScanActivity::class.java))
        }

        // Method to start QR code scanning
        fun startScanQRCode(view: View) {
            // Create an IntentIntegrator to initiate QR code scanning
            val integrator = IntentIntegrator(this)
            // Customize the prompt message if needed
            integrator.setPrompt("Scan a QR Code")
            // Start the scanning process
            integrator.initiateScan()
        }

        // Override onActivityResult to handle the result of QR code scanning
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            // Check if the result is from QR code scanning
            if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
                // Retrieve the scanned content
                val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                if (result != null && result.contents != null) {
                    // Handle the scanned content here (e.g., display it in a toast)
                    Toast.makeText(this, "Scanned QR Code: ${result.contents}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
