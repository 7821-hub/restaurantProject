package fr.isen.bernoussi.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CategoryActivity : AppCompatActivity() {
    private lateinit  var adapter:  RecyclerAdapter
    val chosenType = intent?.extras?.get("CATEGORY_NAME")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

    }
    enum class Type{
        ENTREES, PLATS, DESSERTS;

        companion object{
            fun categoryTitle(type: Type?): String{
                return when (type){
                    ENTREES -> "ENTREES"
                    PLATS -> "PLATS"
                    DESSERTS -> "DESSERTS"
                    else ->""
                }
            }
        }
    }
}
