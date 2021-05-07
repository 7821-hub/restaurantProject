package fr.isen.bernoussi.androiderestaurant

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BLEScanAdapter(
    private val scanResults: MutableList<ScanResult>,
    private val deviceClickListener: (BluetoothDevice) -> Unit
) :
    RecyclerView.Adapter<BLEScanAdapter.DevicesViewHolder>() {
    class DevicesViewHolder(devicesView: View) : RecyclerView.ViewHolder(devicesView) {
        val deviceName: TextView = devicesView.findViewById(R.id.tvNameValue)
        val deviceMac: TextView = devicesView.findViewById(R.id.tvAddressValue)
        val btConnect: Button = devicesView.findViewById(R.id.btConnect)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevicesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ble_device_cell, parent, false)
        return DevicesViewHolder(view)
    }

    override fun getItemCount(): Int = scanResults.size

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        holder.deviceName.text = scanResults[position].device.name ?: "Device Unknown"
        holder.deviceMac.text = scanResults[position].device.address
        holder.btConnect.setOnClickListener {
            deviceClickListener.invoke(scanResults[position].device)
        }
    }


    fun addDeviceToList(result: ScanResult) {
        val index = scanResults.indexOfFirst { it.device.address == result.device.address }
        if (index != -1) {
            scanResults[index] = result
        } else {
            scanResults.add(result)
        }
    }

    fun clearResults() {
        scanResults.clear()
    }

}
