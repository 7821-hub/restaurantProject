package fr.isen.bernoussi.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.bernoussi.androiderestaurant.data.Dishes
import fr.isen.bernoussi.androiderestaurant.databinding.ActivityDishBinding

class DishActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityDishBinding
    private lateinit var dish: Dishes
    private var quantity: Int = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDishBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        dish = intent.getSerializableExtra("dish") as Dishes

        displayInfo()

        if (dish != null) {
            _binding.dishDetailTitle  .text = dish!!.title
            _binding.carousel.pageCount = dish.images.size
            _binding.carousel.setImageListener(imageListener)
        }

        _binding.btMoins.setOnClickListener {
            if(quantity == 1) {
                Toast.makeText(applicationContext, "Can't set less than 1 dish", Toast.LENGTH_SHORT).show()
            }else {
                quantity = quantity-1
                _binding.dishDetailPrice.text = "${(quantity*(dish.prices[0].price.toDouble()))} €"
                _binding.dishQuantity.text = quantity.toString()
            }
        }

        _binding.btPlus.setOnClickListener{
            quantity = quantity+1
            _binding.dishDetailPrice.text = "${(quantity*(dish.prices[0].price.toDouble()))} €"
            _binding.dishQuantity.text = quantity.toString()
        }
    }

    val imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            if (dish == null) {
                Picasso.get().load("https://www.google.com/search?q=carpaccio+de+saumon&client=firefox-b-d&sxsrf=ALeKk03fIsVEYRqbKKtdadI3BjHE8VL_HQ:1620414704798&tbm=isch&source=iu&ictx=1&fir=40nrQdWr5Q05rM%252Cv1_U2jy3FxpBKM%252C%252Fg%252F1q6g8_svr&vet=1&usg=AI4_-kQmhbrUsVH2YzAlmRBiyGXSj0PAJA&sa=X&ved=2ahUKEwjh1PiWo7jwAhXxDWMBHRt3DmAQ_B16BAgeEAE#imgrc=b7WWjPRV0V7dMM").into(imageView)
            } else {
                Picasso.get().load(dish.images.get(position)).into(imageView)
            }
        }
    }

    private fun displayInfo() {
        _binding.dishDetailTitle.text = dish.title
        _binding.dishDetailPrice.text = "${dish.prices[0].price} €"
        _binding.dishQuantity.text = quantity.toString()
    }
}