package fr.isen.bernoussi.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.bernoussi.androiderestaurant.CategoryActivity.Type.Companion.categoryTitle
import fr.isen.bernoussi.androiderestaurant.data.Dishes
import fr.isen.bernoussi.androiderestaurant.data.RequestResult
import fr.isen.bernoussi.androiderestaurant.databinding.ActivityCategoryBinding
import org.json.JSONObject

class CategoryActivity : AppCompatActivity(), RecyclerAdapter.onItemClickedListener {

    private lateinit var _binding: ActivityCategoryBinding
    private lateinit var adapter: RecyclerAdapter
    lateinit var chosenType: Type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        chosenType = intent?.extras?.get("CATEGORY_NAME") as Type
        _binding.titleMenu.text = categoryTitle(chosenType)

        getMenuInformation(chosenType)
    }

    private fun getMenuInformation(type: Type) {
        Log.d(
            "requestStart :",
            "startRequest"
        )
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val queue = Volley.newRequestQueue(this)
        val jsonObject = JSONObject().put("id_shop", 1)
        val stringRequest = JsonObjectRequest(Request.Method.POST, url,jsonObject,
            Response.Listener<JSONObject> { response ->
                val data = Gson().fromJson(response.toString(), RequestResult::class.java)
                Log.d("request : response", response.toString())
                displayData(data, type)
            },
            Response.ErrorListener { Log.d("request :", it.toString())})

        queue.add(stringRequest)
    }

    private fun displayData(data: RequestResult?, type: Type) {
        var curseur = -1
        when (type){
            Type.ENTREES -> curseur = 0
            Type.PLATS -> curseur = 1
            Type.DESSERTS -> curseur = 2
        }
        if (curseur != -1) {
            _binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
            _binding.menuRecyclerView.adapter = RecyclerAdapter(data!!.data[curseur].dishes,this)
        }
    }


    enum class Type {
        ENTREES, PLATS, DESSERTS;

        companion object {
            fun categoryTitle(type: Type?): String {
                return when (type) {
                    ENTREES -> "ENTREES"
                    PLATS -> "PLATS"
                    DESSERTS -> "DESSERTS"
                    else -> ""
                }
            }
        }
    }

    override fun onItemClicked(dish: Dishes) {
        val intent = Intent(this, DishActivity::class.java)
        intent.putExtra("dish",dish)
        startActivity(intent)
    }
}
