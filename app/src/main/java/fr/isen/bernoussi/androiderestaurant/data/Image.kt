package fr.isen.bernoussi.androiderestaurant.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("images") val url: String
) : Serializable