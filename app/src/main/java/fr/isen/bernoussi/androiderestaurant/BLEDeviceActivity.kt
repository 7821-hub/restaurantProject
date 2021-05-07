package fr.isen.bernoussi.androiderestaurant

import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bernoussi.androiderestaurant.databinding.ActivityBleDeviceBinding

class BLEDeviceActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityBleDeviceBinding
    private var device: BluetoothDevice? = null
    private lateinit var bleDeviceAdapter: BLEDeviceAdapter
    private lateinit var bluetoothGatt: BluetoothGatt
    var bleStatus: String = ""
    private lateinit var serviceList: MutableList<BluetoothGattService>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBleDeviceBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        device = intent.getParcelableExtra("device")

        bluetoothGatt = device?.connectGatt(this, false, gattCallback)!!

        serviceList = bluetoothGatt.services

        bleDeviceAdapter = BLEDeviceAdapter(serviceList)

        _binding.recyclerViewServices.layoutManager = LinearLayoutManager(this)
        _binding.recyclerViewServices.adapter = bleDeviceAdapter

        if (device?.name.isNullOrEmpty())
            _binding.tvDeviceTitle.text = device?.name
        else
            _binding.tvDeviceTitle.text = "Unknown Name"

        _binding.tvAddress.text = device?.address

    }


    private val gattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    runOnUiThread {
                        bleStatus = STATE_CONNECTED.toString()
                    }
                    bluetoothGatt.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        bleStatus = STATE_DISCONNECTED.toString()
                    }
                }
            }
        }

    }
}