package fr.isen.bernoussi.androiderestaurant


import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import fr.isen.bernoussi.androiderestaurant.databinding.ActivityBleScanBinding




class BLEScanActivity : AppCompatActivity(){
    private lateinit var binding:ActivityBleScanBinding
    private var isScanning = false
    private var bluetoothAdapter: BluetoothAdapter? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter
        startBLEifPossible()


        binding.bleScanPlayPauseAction.setOnClickListener{
            togglePlayPauseAction()
        }
        binding.bleScanTitle.setOnClickListener(){
            togglePlayPauseAction()
        }
    }

    private fun startBLEifPossible(){
        when{
            !isDeviceHasBLESupport() || bluetoothAdapter == null ->{
                Toast.makeText(
                     this, "Cet appareil n'est pas compatible avec le Bluetooth Low Energy",Toast.LENGTH_SHORT).show()
            }
            !(bluetoothAdapter?.isEnabled ?: false)->{
                //je dois activer le bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED->{
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION_LOCATION
                    )
                }
            else -> {
                // on peut faire le ble
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode,data)
        if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK){
            startBLEifPossible()
        }
    }
    private fun isDeviceHasBLESupport(): Boolean =
        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)


    private fun togglePlayPauseAction(){
        if(isScanning){
            binding.bleScanTitle.text = getString(R.string.ble_scan_pause_title)
            binding.bleScanPlayPauseAction.setImageResource(R.drawable.ic_pause)
            binding.loadingProgress.isVisible = true
            binding.divider.isVisible = false
        }
        else{
            binding.bleScanTitle.text = getString(R.string.ble_scan_play_title)
            binding.bleScanPlayPauseAction.setImageResource(R.drawable.ic_play)
            binding.loadingProgress.visibility = View.INVISIBLE
            binding.divider.isVisible = true
        }

    }
    companion object {
        const private val REQUEST_ENABLE_BT = 33
        const private val REQUEST_PERMISSION_LOCATION = 22
    }
}

