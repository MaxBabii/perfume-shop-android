package com.maksym.perfumeshop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toDuration

class CartActivity : AppCompatActivity() {
    private lateinit var perfumeLayouts: List<FrameLayout>
    private lateinit var brand: List<TextView>
    private lateinit var model: List<TextView>
    private lateinit var type: List<TextView>
    private lateinit var fragrance: List<TextView>
    private lateinit var price: List<TextView>
    private lateinit var ml: List<TextView>
    private lateinit var plusBtn: List<ImageButton>
    private lateinit var minusBtn: List<ImageButton>
    private lateinit var count: List<TextView>
    private lateinit var images: List<ImageView>
    private lateinit var totalPrice: TextView
    private lateinit var toOrderBtn: ImageButton
    private var perfumeImages: IntArray = intArrayOf(
        R.drawable.parfume1,
        R.drawable.parfume2,
        R.drawable.perfume3,
        R.drawable.perfume4,
        R.drawable.perfume5,
        R.drawable.perfume6,
        R.drawable.perfume7,
        R.drawable.perfume8,
        R.drawable.perfume9,
        R.drawable.perfume10
    )
    private var brandTexts = arrayOf("Versace", "Dior", "Chanel", "Armani", "Rabbane", "Hugo", "Prada", "Armani", "Huggo", "Lacoste")
    private var typeTexts = arrayOf("perfume", "perfume", "perfume", "toilet water", "toilet water", "perfume", "perfume", "toilet water", "toilet water", "toilet water")
    private var modelTexts = arrayOf("Eros", "Sauvage", "Bleu", "Stronger", "1 million", "Boss", "Luna", "Code", "Jeans", "L’Homme")
    private var priceTexts = arrayOf("120$", "100$", "150$", "100$", "110$", "90$", "50$", "50$", "60$", "40$")
    private var fragranceTexts = arrayOf("Ice", "Sweet", "Floral", "Bergamot", "Grapefruit", "Ginger", "Tonka beans", "Bergamot", "Vetiver", "Vetiver")
    private var mlTexts = arrayOf("100ml", "50ml", "120ml", "50ml", "100ml", "50ml", "50ml", "50ml", "100ml", "50ml")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        totalPrice = findViewById(R.id.total_price)
        toOrderBtn = findViewById(R.id.to_order_btn)
        val backBtn: ImageButton = findViewById(R.id.back_cart_btn)
        initObject()
        val sharedPreferences = getSharedPreferences("myCart", MODE_PRIVATE)
        var cartList = sharedPreferences.getStringSet("listCart", mutableSetOf())
        if (cartList != null) {
            for (i in cartList!!.indices){
                val currentPrice: Int = priceTexts[i].split("$")[0].toInt()
                val currentCount: Int = count[i].text.toString().toInt()
                val j = cartList.elementAt(i).split(":")[0].toInt()
                perfumeLayouts[i].isVisible = true
                brand[i].text = brandTexts[j]
                model[i].text = modelTexts[j]
                type[i].text = typeTexts[j]
                fragrance[i].text = fragranceTexts[j]
                price[i].text = priceTexts[j]
                ml[i].text = mlTexts[j]
                images[i].setImageResource(perfumeImages[j])
                count[i].text = cartList.elementAt(i).split(":")[1]
                calculateTotalPrice(currentPrice, currentCount)
                plusBtn.elementAt(i).setOnClickListener {
                    val currentPrice: Int = priceTexts[i].split("$")[0].toInt()
                    val currentCount: Int = count[i].text.toString().toInt()
                    count[i].text = (count.elementAt(i).text.toString().toInt() + 1).toString()
                    val newValue = "$j:" + count[i].text
                    val newSetList = cartList!!.toList().toMutableList()
                    calculateTotalPrice(currentPrice, currentCount)
                    newSetList[i] = newValue
                    cartList = newSetList.toSet()
                    val editor = sharedPreferences.edit()
                    editor.putStringSet("listCart", cartList)
                    editor.apply()
                }
                minusBtn.elementAt(i).setOnClickListener {
                    count[i].text = (count.elementAt(i).text.toString().toInt() - 1).toString()
                    if(count.elementAt(i).text.toString().toInt() > 0){
                    val newValue = "$j:" + count[i].text
                    val newSetList = cartList!!.toList().toMutableList()

                    newSetList[i] = newValue
                    cartList = newSetList.toSet()
                    val editor = sharedPreferences.edit()
                    editor.putStringSet("listCart", cartList)
                    editor.apply()
                    }else {
                        cartList!!.remove("$j:1")
                        val editor = sharedPreferences.edit()
                        editor.putStringSet("listCart", cartList)
                        editor.apply()
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast)
                        finish()
                    }
                }
            }
        }
        toOrderBtn.setOnClickListener {
            val brands = ArrayList<String>()
            val counts = ArrayList<String>()
            val prices = ArrayList<String>()
            for(i in perfumeLayouts.indices){
                if(perfumeLayouts.elementAt(i).isVisible){
                    brands.add(brand.elementAt(i).text.toString())
                    counts.add(count.elementAt(i).text.toString())
                    prices.add(price.elementAt(i).text.toString())
                }
            }

            if(cartList!!.isNotEmpty()){
                cartList!!.clear()
                val intent = Intent(this, OrderActivity::class.java)
                intent.putStringArrayListExtra("nameArray", brands)
                intent.putStringArrayListExtra("countArray", counts)
                intent.putStringArrayListExtra("priceArray", prices)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast)
                finish()
            }

        }
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        hideUi()
    }
    private fun calculateTotalPrice(price: Int, count: Int){
        val currentTotalPrice = totalPrice.text.split("$")[0].toInt()
        val newPrice = currentTotalPrice + (price * count)
        totalPrice.text = "$newPrice$"
    }
    private fun initObject(){
        perfumeLayouts = listOf(
            findViewById(R.id.first_layout),
            findViewById(R.id.second_layout),
            findViewById(R.id.third_layout),
            findViewById(R.id.four_layout),
            findViewById(R.id.five_layout),
            findViewById(R.id.six_layout),
            findViewById(R.id.seven_layout),
            findViewById(R.id.eight_layout),
            findViewById(R.id.nine_layout),
            findViewById(R.id.ten_layout)
        )
        brand = listOf(
            findViewById(R.id.perfume1_name_cart),
            findViewById(R.id.perfume2_name_cart),
            findViewById(R.id.perfume3_name_cart),
            findViewById(R.id.perfume4_name_cart),
            findViewById(R.id.perfume5_name_cart),
            findViewById(R.id.perfume6_name_cart),
            findViewById(R.id.perfume7_name_cart),
            findViewById(R.id.perfume8_name_cart),
            findViewById(R.id.perfume9_name_cart),
            findViewById(R.id.perfume10_name_cart)
        )
        model = listOf(
            findViewById(R.id.perfume1_model_cart),
            findViewById(R.id.perfume2_model_cart),
            findViewById(R.id.perfume3_model_cart),
            findViewById(R.id.perfume4_model_cart),
            findViewById(R.id.perfume5_model_cart),
            findViewById(R.id.perfume6_model_cart),
            findViewById(R.id.perfume7_model_cart),
            findViewById(R.id.perfume8_model_cart),
            findViewById(R.id.perfume9_model_cart),
            findViewById(R.id.perfume10_model_cart)
        )
        type = listOf(
            findViewById(R.id.perfume1_type_cart),
            findViewById(R.id.perfume2_type_cart),
            findViewById(R.id.perfume3_type_cart),
            findViewById(R.id.perfume4_type_cart),
            findViewById(R.id.perfume5_type_cart),
            findViewById(R.id.perfume6_type_cart),
            findViewById(R.id.perfume7_type_cart),
            findViewById(R.id.perfume8_type_cart),
            findViewById(R.id.perfume9_type_cart),
            findViewById(R.id.perfume10_type_cart)
        )
        fragrance = listOf(
            findViewById(R.id.perfume1_type_fragrance_cart),
            findViewById(R.id.perfume2_type_fragrance_cart),
            findViewById(R.id.perfume3_type_fragrance_cart),
            findViewById(R.id.perfume4_type_fragrance_cart),
            findViewById(R.id.perfume5_type_fragrance_cart),
            findViewById(R.id.perfume6_type_fragrance_cart),
            findViewById(R.id.perfume7_type_fragrance_cart),
            findViewById(R.id.perfume8_type_fragrance_cart),
            findViewById(R.id.perfume9_type_fragrance_cart),
            findViewById(R.id.perfume10_type_fragrance_cart)
        )
        price = listOf(
            findViewById(R.id.perfume1_price_cart),
            findViewById(R.id.perfume2_price_cart),
            findViewById(R.id.perfume3_price_cart),
            findViewById(R.id.perfume4_price_cart),
            findViewById(R.id.perfume5_price_cart),
            findViewById(R.id.perfume6_price_cart),
            findViewById(R.id.perfume7_price_cart),
            findViewById(R.id.perfume8_price_cart),
            findViewById(R.id.perfume9_price_cart),
            findViewById(R.id.perfume10_price_cart)
        )
        ml = listOf(
            findViewById(R.id.perfume1_ml_cart),
            findViewById(R.id.perfume2_ml_cart),
            findViewById(R.id.perfume3_ml_cart),
            findViewById(R.id.perfume4_ml_cart),
            findViewById(R.id.perfume5_ml_cart),
            findViewById(R.id.perfume6_ml_cart),
            findViewById(R.id.perfume7_ml_cart),
            findViewById(R.id.perfume8_ml_cart),
            findViewById(R.id.perfume9_ml_cart),
            findViewById(R.id.perfume10_ml_cart)
        )
        plusBtn = listOf(
            findViewById(R.id.perfume1_plus_cart),
            findViewById(R.id.perfume2_plus_cart),
            findViewById(R.id.perfume3_plus_cart),
            findViewById(R.id.perfume4_plus_cart),
            findViewById(R.id.perfume5_plus_cart),
            findViewById(R.id.perfume6_plus_cart),
            findViewById(R.id.perfume7_plus_cart),
            findViewById(R.id.perfume8_plus_cart),
            findViewById(R.id.perfume9_plus_cart),
            findViewById(R.id.perfume10_plus_cart)
        )
        minusBtn = listOf(
            findViewById(R.id.perfume1_minus_cart),
            findViewById(R.id.perfume2_minus_cart),
            findViewById(R.id.perfume3_minus_cart),
            findViewById(R.id.perfume4_minus_cart),
            findViewById(R.id.perfume5_minus_cart),
            findViewById(R.id.perfume6_minus_cart),
            findViewById(R.id.perfume7_minus_cart),
            findViewById(R.id.perfume8_minus_cart),
            findViewById(R.id.perfume9_minus_cart),
            findViewById(R.id.perfume10_minus_cart)
        )
        count = listOf(
            findViewById(R.id.perfume1_count_cart),
            findViewById(R.id.perfume2_count_cart),
            findViewById(R.id.perfume3_count_cart),
            findViewById(R.id.perfume4_count_cart),
            findViewById(R.id.perfume5_count_cart),
            findViewById(R.id.perfume6_count_cart),
            findViewById(R.id.perfume7_count_cart),
            findViewById(R.id.perfume8_count_cart),
            findViewById(R.id.perfume9_count_cart),
            findViewById(R.id.perfume10_count_cart)
        )
        images = listOf(
            findViewById(R.id.perfume1_cart),
            findViewById(R.id.perfume2_cart),
            findViewById(R.id.perfume3_cart),
            findViewById(R.id.perfume4_cart),
            findViewById(R.id.perfume5_cart),
            findViewById(R.id.perfume6_cart),
            findViewById(R.id.perfume7_cart),
            findViewById(R.id.perfume8_cart),
            findViewById(R.id.perfume9_cart),
            findViewById(R.id.perfume10_cart)
        )
    }
    private fun hideUi() {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LOW_PROFILE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsController = ViewCompat.getWindowInsetsController(window.decorView)
            insetsController?.let {
                it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                it.hide(WindowInsetsCompat.Type.systemBars())
            }
        } else {
            val decorView = window.decorView
            decorView.systemUiVisibility = uiOptions
        }
    }
}