package fr.isen.bernoussi.androiderestaurant.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("name_fr") val name: String,
    @SerializedName("items") val dishes:List<Dishes>
): Serializable