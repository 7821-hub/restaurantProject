package fr.isen.bernoussi.androiderestaurant


import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bernoussi.androiderestaurant.databinding.ActivityBleScanBinding


class BLEScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBleScanBinding
    private var isScanning = false
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val handler = Handler()
    private lateinit var bleScanAdapter: BLEScanAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter
        startBLEifPossible()

        bleScanAdapter = BLEScanAdapter(mutableListOf()) {
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
            val intent = Intent(this, BLEDeviceActivity::class.java)
            intent.putExtra("device", it )
            startActivity(intent)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = bleScanAdapter

        binding.bleScanPlayPauseAction.setOnClickListener {
            togglePlayPauseAction()
        }
        binding.bleScanTitle.setOnClickListener() {
            togglePlayPauseAction()
        }
    }

    private fun startBLEifPossible() {
        when {
            !isDeviceHasBLESupport() || bluetoothAdapter == null -> {
                Toast.makeText(
                    this,
                    "Cet appareil n'est pas compatible avec le Bluetooth Low Energy",
                    Toast.LENGTH_SHORT
                ).show()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                //je dois activer le bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
            }
            else -> {
                Log.d("ScanDevices", "onRequestPermissionsResult(not PERMISSION")

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            startBLEifPossible()
        }
    }

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000
    private fun scanLeDevice() {
        bluetoothAdapter?.bluetoothLeScanner?.let { scanner ->
            if (!isScanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    isScanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                isScanning = true
                scanner.startScan(leScanCallback)
            } else {
                isScanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("BLEScan", "CC LE SCAN")
            bleScanAdapter.addDeviceToList(result)
            bleScanAdapter.notifyDataSetChanged()

        }
    }

    private fun isDeviceHasBLESupport(): Boolean =
        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)


    private fun togglePlayPauseAction() {
        if (!isScanning) {
            binding.bleScanTitle.text = getString(R.string.ble_scan_pause_title)
            binding.bleScanPlayPauseAction.setImageResource(R.drawable.ic_pause)
            binding.loadingProgress.isVisible = true
            binding.divider.isVisible = false
            scanLeDevice()
        } else {
            binding.bleScanTitle.text = getString(R.string.ble_scan_play_title)
            binding.bleScanPlayPauseAction.setImageResource(R.drawable.ic_play)
            binding.loadingProgress.visibility = View.INVISIBLE
            binding.divider.isVisible = true
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
        }

    }

    companion object {
        const private val REQUEST_ENABLE_BT = 33
        const private val REQUEST_PERMISSION_LOCATION = 22
    }
}

