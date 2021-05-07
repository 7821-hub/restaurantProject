package fr.isen.bernoussi.androiderestaurant

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.bernoussi.androiderestaurant.data.Dishes
import fr.isen.bernoussi.androiderestaurant.databinding.CellDishBinding


class RecyclerAdapter(
    val dishes: List<Dishes>,
    val onClikcListener: CategoryActivity
) : RecyclerView.Adapter<RecyclerAdapter.DishViewHodler>() {

    private lateinit var _binding: CellDishBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHodler {
        _binding = CellDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHodler(_binding.root)
    }

    override fun getItemCount(): Int = dishes.size

    override fun onBindViewHolder(holder: DishViewHodler, position: Int) {
        holder.title.text = dishes[position].title
        if (dishes[position].getFirstPicture() != null)
            Picasso.get().load(dishes[position].getFirstPicture()).into(holder.image)
        holder.price.text = dishes[position].prices[0].toString()

        holder.image.setOnClickListener { onClikcListener.onItemClicked(dish = dishes[position]) }
    }

    class DishViewHodler(view: View): RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.dishImage)
        val price = view.findViewById<TextView>(R.id.dishPrice)
        val title = view.findViewById<TextView>(R.id.dishTitle)
    }

    interface onItemClickedListener{
        fun onItemClicked(dish: Dishes)
    }
}





