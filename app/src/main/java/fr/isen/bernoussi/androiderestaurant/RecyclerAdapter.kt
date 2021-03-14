package fr.isen.bernoussi.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bernoussi.androiderestaurant.data.Dishes
import kotlinx.android.synthetic.main.item_user.view.*

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name = itemView.textView

}

class RecyclerAdapter(var dishes: MutableList<Dishes>) : RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val dish = dishes.get(position)
        myHolder.name.text = dish.name
    }
}





