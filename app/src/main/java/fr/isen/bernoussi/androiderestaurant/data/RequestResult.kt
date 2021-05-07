package fr.isen.bernoussi.androiderestaurant.data

import com.google.gson.annotations.SerializedName

data class RequestResult(@SerializedName("data") val data: List<Data>)