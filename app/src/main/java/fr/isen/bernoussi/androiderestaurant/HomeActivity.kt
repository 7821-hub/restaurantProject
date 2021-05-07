package fr.isen.bernoussi.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.bernoussi.androiderestaurant.databinding.HomeActivityBinding
import kotlinx.android.synthetic.main.activity_ble_scan.*
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrees.setOnClickListener {
            StartCategoryActivity(CategoryActivity.Type.ENTREES)
        }
        binding.btnPlats.setOnClickListener {
            StartCategoryActivity(CategoryActivity.Type.PLATS)
        }
        binding.btnDesserts.setOnClickListener {
            StartCategoryActivity(CategoryActivity.Type.DESSERTS)
        }
        binding.BtnBle.setOnClickListener{
            val intent = Intent(this, BLEScanActivity::class.java)
            startActivity(intent)
        }
    }


    private fun StartCategoryActivity(type: CategoryActivity.Type) {
        //Log.d("Home activity destroy", "destruction")
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, type)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}

