package fr.isen.bernoussi.androiderestaurant

import android.bluetooth.BluetoothGattService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BLEDeviceAdapter(
    val serviceList: MutableList<BluetoothGattService>
) :
    RecyclerView.Adapter<BLEDeviceAdapter.ServiceViewHolder>() {
    class ServiceViewHolder(services: View) : RecyclerView.ViewHolder(services) {
        val serviceUuid: TextView = services.findViewById(R.id.tvServiceUuid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_ble_device_service_cell, parent, false)
        return ServiceViewHolder(view)
    }

    override fun getItemCount(): Int = serviceList.size

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.serviceUuid.text = serviceList[position].uuid.toString()
    }

}